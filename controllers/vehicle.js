import vehicle from "../models/vehicle.js";




export function getAllCarByOwnership(req, res) {
  vehicle
  .find({"ownership": req.params.ownership ,"user_id":req.params.user_id})

  .then(vehicle=> {
      res.status(200).json(vehicle);
  })
  .catch(err => {
      res.status(500).json({ error: err });
  });
}

export function getAll(req, res) {
    vehicle
      .find({})
      .then(docs => {
          res.status(200).json(docs);
      })
      .catch(err => {
          res.status(500).json({ error: err });
      });
}


export function getAllVehicleByIdUser(req, res) {
    vehicle
  .find({"user_id": req.params.user_id })

  .then(vehicle=> {
      res.status(200).json(vehicle);
  })
  .catch(err => {
      res.status(500).json({ error: err });
  });
}


export function updateVehicle(req, res) {
    vehicle.updateOne({ _id: req.params._id }, { $set: req.body })
    .then((doc) => {
      res.status(200).json("vehicle updated");
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

export function  addVehicle(req, res) {
    vehicle
  .create({
    license: req.body.license,
    nickname: req.body.nickname,
    ownership : req.body.ownership,
    user_id: req.body.user_id
  })
    .then((newVehicle) => {
      res.status(201).json(newVehicle);
    })
    .catch((err) => {
      res.status(400).json(err);
    });
}

export function updataVehicle(req, res) {
    vehicle
      .findOneAndUpdate({ "_id":req.params.idvehicle },
          { "license": req.body.license,
            "nickname": req.body.nickname
        })
      .then(doc => {
          res.status(200).json(doc);
      })
      .catch(err => {
          res.status(500).json({ error: err });
      });
}

export function getVehicleById(req, res) {
    vehicle.findOne({ _id: req.params.id })
    .then((docs) => {
      res.status(200).json(docs);
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

/**
 * Supprimer un seul document
 */
export function deleteOnce(req, res) {
    vehicle
        .findOneAndRemove({ "_id": req.params.idvehicle })
        .then(doc => {
            res.status(200).json(doc);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
  }