import  jwt  from 'jsonwebtoken';
import createError from 'http-errors';
import { OAuth2Client } from 'google-auth-library';
import User from '../models/user.js';


const client = new OAuth2Client("840482359348-06tjqpl7am4qca4snph140qvk3l5j3ae.apps.googleusercontent.com");

export async function verify(req, res) {
  try {
    if (!req || !req.params || !req.params.id_token) {
      // Handle the case when 'req' object or 'id_token' parameter is missing
      res.status(400).json({ error: "Missing id_token parameter" });
      return;
    }

    const idToken = req.params.id_token;
    const ticket = await client.verifyIdToken({
      idToken: idToken,
      audience: "840482359348-06tjqpl7am4qca4snph140qvk3l5j3ae.apps.googleusercontent.com",
    });

    const payload = ticket.getPayload();
    const userid = payload['sub'];
    const email = payload['email'];

    // Check if the user already exists in the database based on the email
    User.findOne({ email })
      .then(async user => {
        if (user) {
          // User already exists, perform login action
          const accessToken = await signAccessToken(user.id);
          // const refreshToken = await signRefreshToken(user.id);
          console.log(accessToken)
          console.log("exisssssts")
          res.status(200).json({
            message: "Login successful",
            accessToken,
            // refreshToken,
            user,
          });
        } else {
          // User doesn't exist, create a new user record
          // Assuming you have a createUser function in your User model
          console.log("doesnt exissssst")
          User.create({
            username: "",
            fullname: "",
            email: email,
            // ... other fields you need to populate in the user record
          })
            .then(async newUser => {
              // Establish an authenticated session for the user (implement your own session management logic)
              const accessToken = await signAccessToken(newUser.id);
              // const refreshToken = await signRefreshToken(newUser.id);
              console.log(newUser.email)
              console.log("user created")
              res.status(200).json({
                message: "User created and logged in",
                accessToken,
                // refreshToken,
                user: newUser,
              });
            })
            .catch(error => {
              console.error("User creation error:", error);
              res.status(500).json({ error: "User creation failed" });
            });
        }
      })
      .catch(error => {
        console.error("User lookup error:", error);
        res.status(500).json({ error: "User lookup failed" });
      });

  } catch (error) {
    console.error("Token verification error:", error);
    res.status(500).json({ error: "Token verification failed" });
  }
}



 



export function  signAccessToken (userId) {
    return new Promise((resolve, reject) => {
      const payload = {_id: userId}
      const secret = 'verySecretValue'
      const options = {
        audience: userId,
      }
      jwt.sign(payload, secret, options, (err, token) => {
        if (err) {
          console.log(err.message)
          reject(createError.InternalServerError())
          return
        }
        resolve(token)
      })
    })
  }

  export default function auth (req, res, next){
    try {
        const token = req.headers.authorization.split(' ')[1];
        const decodedToken = jwt.verify(token, 'verySecretValue');
        const userId = decodedToken._id;
        req.auth = {
            userId: userId
        };
     next();
    } catch(error) {
        res.status(401).json({ error });
    }
 };

export function verifyAccessToken (req, res, next) {
    if (!req.headers['authorization']) return next(createError.Unauthorized())
    const authHeader = req.headers['authorization']
    const bearerToken = authHeader.split(' ')
    const token = bearerToken[1]
    JWT.verify(token, 'verySecretValue', (err, payload) => {
      if (err) {
        const message =
          err.name === 'JsonWebTokenError' ? 'Unauthorized' : err.message
        return next(createError.Unauthorized(message))
      }
      req.payload = payload
      next()
    })
  }
  
  export function signRefreshToken (userId) {
    return new Promise((resolve, reject) => {
      const payload = {}
      const secret = 'verySecretValue'
      const options = {
        expiresIn: '1y',
        audience: userId,
      }
      jwt.sign(payload, secret, options, (err, token) => {
        if (err) {
          console.log(err.message)
          // reject(err)
          reject(createError.InternalServerError())
        }

        client.SET(userId, token, 'EX', 365 * 24 * 60 * 60, (err, reply) => {
          if (err) {
            console.log(err.message)
            reject(createError.InternalServerError())
            return
          }
          resolve(token)
        })
      })
    })
  }

  export function verifyRefreshToken (refreshToken) {
    return new Promise((resolve, reject) => {
      jwt.verify(
        refreshToken,
        'verySecretValue',
        (err, payload) => {
          if (err) return reject(createError.Unauthorized())
          const userId = payload.aud
          client.GET(userId, (err, result) => {
            if (err) {
              console.log(err.message)
              reject(createError.InternalServerError())
              return
            }
            if (refreshToken === result) return resolve(userId)
            reject(createError.Unauthorized())
          })
        }
      )
    })
  }