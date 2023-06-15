import express from "express";

import {
  
  updataVehicle,
  getAllVehicleByIdUser,
  getAll,
  getVehicleById,
  addVehicle,
  deleteOnce,
  getAllCarByOwnership
  
} from "../controllers/vehicle.js";

const router = express.Router();

router
.route("/")
.get(getAll)


router
    .route('/:id')
    .get(getVehicleById)

router
    .route('/:idvehicle')  
    .put(updataVehicle)
    .delete(deleteOnce)

router
  .route('/user/:user_id')
  //.post(addBudget)
  .get(getAllVehicleByIdUser)

router.route('/user').post(addVehicle)
router.route('/:user_id/:ownership').get(getAllCarByOwnership)

export default router;