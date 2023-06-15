import express from "express";

import {
  
  updataParking,
  getAllParkingByIdUser,
  getAll,
  getParkingById,
  addParking,
  deleteOnce
  
} from "../controllers/parking.js";

const router = express.Router();

router
.route("/")
.get(getAll)


router
    .route('/:id')
    .get(getParkingById)

router
    .route('/:idparking')  
    .put(updataParking)
    .delete(deleteOnce)

router
  .route('/user/:user_id')
  //.post(addBudget)
  .get(getAllParkingByIdUser)

router.route('/user').post(addParking)

export default router;