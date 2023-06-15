import User from '../models/user.js';
import bcrypt from 'bcryptjs';
import nodemailer from 'nodemailer'
import  jwt  from 'jsonwebtoken';
import { signAccessToken,signRefreshToken} from '../middlewares/auth.js';
import user from '../models/user.js';
import { Schema } from 'mongoose';
import crypto from "crypto"


var transporter = nodemailer.createTransport({
    service: "gmail",
    secure: true, // use SSL
    auth: {
      user: "bazdeh.youssef@esprit.tn",
      pass: "191JMT2047",
    },
  });
  
  
  export async function SendEmail(email)  {
  
      var mailOptions = {
        from: "bazdeh.youssef@esprit.tn",
        to: email,
        text: "Hello Welcome to GoodWed, thanks for joining us <3",
        subject: "GoodWed",
        
      };
  
      transporter.sendMail(mailOptions, function (error, info) {
        if (error) {
          res.json({ message: "error sending" });
          console.log(error);
        } else {
          res.status(205).json({
            message: "Email Has Sent!"
           
          });
          console.log("email sent successfully")
        }
      });
    
  };

  login: async (req, res, next) => {
    try {
      const result = await authSchema.validateAsync(req.body)
      const user = await User.findOne({ email: result.email })
      if (!user) throw createError.NotFound('User not registered')

      const isMatch = await user.isValidPassword(result.password)
      if (!isMatch)
        throw createError.Unauthorized('Username/password not valid')

      const accessToken = await signAccessToken(user.id)
      const refreshToken = await signRefreshToken(user.id)
            
      res.send({ accessToken, refreshToken })
    } catch (error) {
      if (error.isJoi === true)
        return next(createError.BadRequest('Invalid Username/Password'))
      next(error)
    }
  }

// ! /login
export async function login(req, res) {
    var password = req.body.password

    User.findOne({"email":req.body.email})
    .then(user => {
        if(user){
          

            bcrypt.compare(password,user.password,async function(err,result){
                if(err){
                    res.json({
                        error : err
                    })
                }
                if(result){
                    const accessToken = await signAccessToken(user.id)
                  // const refreshToken = await signRefreshToken(user.id)
                    res.status(200).json({
                        message: 'Login succesfull',
                        accessToken ,
                       // refreshToken,
                        user
                    
                    })
                }else {
                    res.status(401).json({
                        message: 'Password does not matched'
                    })
                }
            })
            
        }else{
            res.status(401).json({
                message : 'no user found !!'
            })
        }
    })
}


/*export async function login(req, res) {
  try {
    const idToken = req.params.id_token; // Assuming the ID token is passed in the request body
    const ticket = await verify(idToken);

    if (ticket) {
      // ID token verification successful
      const { sub, email, name } = ticket.getPayload();
      const userId = sub;

      User.findOne({ "email": email })
        .then(async user => {
          if (user) {
            bcrypt.compare(password, user.password, async function (err, result) {
              if (err) {
                res.status(500).json({ error: err });
              }
              if (result) {
                const accessToken = await signAccessToken(user.id);
                // const refreshToken = await signRefreshToken(user.id);
                res.status(200).json({
                  message: 'Login successful',
                  accessToken,
                  // refreshToken,
                  user
                });
              } else {
                res.status(401).json({
                  message: 'Password does not match'
                });
              }
            });
          } else {
            // User doesn't exist in the database
            // Create a new user record
            // Assuming you have a createUser function in your User model
            //User.createUser(userId, email, name);
            console.log("user is not in my database")
            // Prompt the user for any additional profile information you require
            // Implement your logic to prompt the user and update the user record in the database

            // Establish an authenticated session for the user (implement your own session management logic)

            const accessToken = await signAccessToken(userId);
            // const refreshToken = await signRefreshToken(userId);
            res.status(200).json({
              message: 'Login successful (new user)',
              accessToken,
              // refreshToken,
              user: { id: userId, email, name }
            });
          }
        })
        .catch(error => {
          console.error("User lookup error:", error);
          res.status(500).json({ error: "User lookup failed" });
        });
    } else {
      // ID token verification failed
      res.status(401).json({ message: "Invalid ID token" });
    }
  } catch (error) {
    console.error("Token verification error:", error);
    res.status(500).json({ error: "Token verification failed" });
  }
}*/


export function getAll(req, res) {
    User
        .find({})
        .then(docs => {
            res.status(200).json(docs);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
}

export async function addOnce(req, res) {
    // Invoquer la m�thode create directement sur le mod�le
    console.log(req.body)
        User
            .create({
                username: req.body.username,
                email: req.body.email,
                password: await bcrypt.hash(req.body.password,10), 
            })
        .then(newUser => {
            res.status(200).json(newUser);
            SendEmail(newUser.email);
        })
        .catch(err => {
            console.log(err)
            res.status(500).json({ error: err });
        });
    

}

  

export function getOnce1(req, res) {
    const userId = req.auth.userId; // Check that req.auth exists and has a userId property
    if (!userId) {
      return res.status(401).json({ error: "Unauthorized" }); // Return an error response if userId is not defined
    }
    User.findOne({ _id: userId })
      .then((doc) => {
        res.status(200).json(doc);
      })
      .catch((err) => {
        console.log(err)
        res.status(500).json({ error: err });
      });
  }
  
export function getUserById(req, res) {
    User.findOne({ _id: req.params.user_id })
      .then((docs) => {
        res.status(200).json(docs);
      })
      .catch((err) => {
        res.status(500).json({ error: err });
      });
  }

/*export function getOnce(req, res) {
    User
        .findOne({ "username": req.params.username })
        .then(doc => {
            res.status(200).json(doc);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
}*/

/**
 * Mettre � jour plusieurs documents
 * Remarque : renommez putOnce par putAll
 */
export function putAll1(req, res) {
    User
        .updateMany({}, { "name": "" })
        .then(doc => {
            res.status(200).json(doc);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
}
/**
 * Updates all fields
 */
export function putOnce(req, res) {
    User
        .findOneAndUpdate({ "_id": req.params.idUser },
            {"username": req.body.username
            ,"fullname": req.body.fullname,
            "email" : req.body.email,
            "datedenaissance" : req.body.datedenaissance,
             "password": req.body.password })
        .then(doc => {
            res.status(200).json(doc);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
}
/**
 * Mettre � jour un seul document
 */
export function updateUser(req, res) {
    User
        .findOneAndUpdate({ "_id": req.params.user_id }, 
        { "username": req.body.username,
            "fullname" : req.body.fullname,
            "email" : req.body.email,
            "datedenaissance" : req.body.datedenaissance})
        .then(docs => {
            res.status(200).json(docs);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
}

/**
 * Supprimer un seul document
 */
export function deleteOnce(req, res) {
    User
        .findOneAndRemove({ _id: req.params.user_id })
        .then(doc => {
            res.status(200).json(doc);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
}

// /**
//  * Supprimer plusieurs documents
//  */
// export function deleteOnce(req, res) {
//     User
//     .remove({ "onSale": false })
//     .then(doc => {
//         res.status(200).json(doc);
//     })
//     .catch(err => {
//         res.status(500).json({ error: err });
//     });
// }
export function putAll(req, res) {
  const userId = req.auth.userId;
  if (!userId) {
    return res.status(401).json({ error: "Unauthorized" }); // Return an error response if userId is not defined
  }
  User.findOneAndUpdate(
    { _id: userId },
    {
      fullname: req.body.fullname,
      username: req.body.username,
      email:req.body.email,
      datedenaissance:req.body.datedenaissance,
      
            /*password:hashPass,
            // Récupérer l'URL de l'image pour l'insérer dans la BD
            image: ${req.protocol}://${req.get('host')}/img/${req.file.filename},
            coins:0,
            steps:0,*/
    }
  )
    .then((doc) => {
      res.status(200).json(doc);
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

export function getOnce(req, res) {
  const userId = req.auth.userId; // Check that req.auth exists and has a userId property
  if (!userId) {
    return res.status(401).json({ error: "Unauthorized" }); // Return an error response if userId is not defined
  }
  User.findOne({ _id: userId })
    .then((doc) => {
      res.status(200).json(doc);
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}


export function forgotPassword(req, res) {
  const { email } = req.body;
  User.findOne({ email })
    .then((user) => {
      if (!user) {
        return res
          .status(401)
          .json({ message: "Aucun utilisateur trouvé avec cet email." });
      }
      const token = crypto.randomBytes(100).toString("hex");
      const resetLink = `http://172.17.6.68:9092/user/reset/${token}`;
      user.resetPasswordToken = token;
      user.resetPasswordExpires = Date.now() + 360000; // 1 hour
      user
        .save()
        .then((updatedUser) => {
          const transporter = nodemailer.createTransport({
            service: "gmail",
            auth: {
              user: "ouerghi.bilel@esprit.tn",
              pass: "doefbhtccfjdgjse",
            },
          });
          const mailOptions = {
            from: "Bilel",
            to: email,
            subject: "Mot de passe oublié",
            html: `
<!doctype html>
<html lang="en-US">
<head>
  <meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
  <title>Reset Password Email Template</title>
  <meta name="description" content="Reset Password Email Template.">
  <style type="text/css">
      a:hover {text-decoration: underline !important;}
  </style>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" style="margin: 0px; background-color: #f2f3f8;" leftmargin="0">
  <!--100% body table-->
  <table cellspacing="0" border="0" cellpadding="0" width="100%" bgcolor="#f2f3f8"
      style="@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;">
      <tr>
          <td>
              <table style="background-color: #f2f3f8; max-width:670px;  margin:0 auto;" width="100%" border="0"
                  align="center" cellpadding="0" cellspacing="0">
                  <tr>
                      <td style="height:80px;">&nbsp;</td>
                  </tr>
                  <tr>
                      <td style="text-align:center;">
                        
                      
                        
                      </td>
                  </tr>
                  <tr>
                      <td style="height:20px;">&nbsp;</td>
                  </tr>
                  <tr>
                      <td>
                          <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0"
                              style="max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);">
                              <tr>
                                  <td style="height:40px;">&nbsp;</td>
                              </tr>
                              <tr>
                                  <td style="padding:0 35px;">
                                      <h1 style="color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;">You have
                                          requested to reset your password</h1>
                                      <span
                                          style="display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;"></span>
                                      <p style="color:#455056; font-size:15px;line-height:24px; margin:0;">
                                          We cannot simply send you your old password. A unique link to reset your
                                          password has been generated for you. To reset your password, click the
                                          following link and follow the instructions.
                                      </p>
                                      <a href="${resetLink}"
                                          style="background:#20e277;text-decoration:none !important; font-weight:500; margin-top:35px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 24px;display:inline-block;border-radius:50px;">Reset
                                          Password</a>
                                  </td>
                              </tr>
                              <tr>
                                  <td style="height:40px;">&nbsp;</td>
                              </tr>
                          </table>
                      </td>
                  <tr>
                      <td style="height:20px;">&nbsp;</td>
                  </tr>
                  <tr>
                      <td style="text-align:center;">
                          <p style="font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;">&copy; <strong>www.Vidoc.com</strong></p>
                      </td>
                  </tr>
                  <tr>
                      <td style="height:80px;">&nbsp;</td>
                  </tr>
              </table>
          </td>
      </tr>
  </table>
  <!--/100% body table-->
</body>
</html>`,
        };
        transporter.sendMail(mailOptions, (error) => {
          if (error) {
            console.log(error);
            return res.status(500).json({ error });
            }
            return res.status(200).json({
              message:
                "Un email de réinitialisation de mot de passe a été envoyé.",
            });
          });
        })
        .catch((error) => {
          console.log("error");
          return res.status(500).json({ error });
        });
    })
    .catch((error) => {
      console.log(error);
      console.log("error");
      return res.status(500).json({ error });
    });
}
///////////////////reset password/////////////////////////////////////

export async function resetPassword(req, res) {
try {
  const user = await User.findOne({
    resetPasswordToken: req.body.token,
    resetPasswordExpires: { $gt: Date.now() },
  });
  if (!user) {
    return res.status(401).json({
      message: "Invalid or expired password reset token.",
    });
  }
  const hashPass = await bcrypt.hash(req.body.password, 10);
  user.password = hashPass;
  user.resetPasswordToken = undefined;
  user.resetPasswordExpires = undefined;

  await user.save();

  return res.status(200).json({ message: "Password reset successful." });
} catch (error) {
  return res.status(500).json({ error });
}
}


export async function generateview(req, res) {
try {
  return res.render("reset-password.ejs", { token: req.params.token });
} catch (error) {
  return res.status(400).json(error);
}
}

