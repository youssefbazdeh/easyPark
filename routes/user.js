import express from 'express';
import { body } from 'express-validator';
import {  SendEmail, deleteOnce, forgotPassword, generateview, getOnce, getOnce1, login, putAll, putOnce, resetPassword } from '../controllers/user.js';
import { getAll, addOnce , getUserById , updateUser} from '../controllers/user.js';

import auth, { verify } from '../middlewares/auth.js';
import fs from "fs"

/**
 * Router est un objet de base sur le module express.
 * Cet objet a des méthodes similaires (.get, .post, .patch, .delete)
 * à l'objet app de type "express()" que nous avons utilisé précédemment.
 */
const router = express.Router();

// Déclarer d'abord la route, puis toutes les méthodes dessus (préfixe spécifié dans server.js)
router
  .route('/signup')
  .get(getAll)
    .post(
        body('fullname').isLength({ min: 5 }),
        body('password').isLength({ min: 8 }),
        body('username').isLength({ min: 4 }),
        addOnce);

 router
        .route('/profile')
        .get(auth,getOnce)
          .put(auth,putAll)

  router
  .route('/:user_id')
  .patch(updateUser)
  .get(getUserById)
  .delete(deleteOnce)
  
  
  router.route("/profile1").get(auth, getOnce1).put(auth,putOnce)


router.route('/login')
  .post(login)

  router.route('/mail')
  .post(SendEmail)
router
  .route('/')
  .get((req, res) => res.status(200).json({ message: 'Hello World!' }))

router
.route('/tokeninfo/:id_token').post(verify)

/**s
 * Maintenant que nous avons créé toutes ces routes,
 * exportons ce module pour l'utiliser dans server.js
 * puisque c'est lui notre entrée principale "main".
 */
router.route("/reset/:token").post(resetPassword);
router.get("/reset/:token",generateview);
router.route("/mdpoublier").post(forgotPassword)

export default router;