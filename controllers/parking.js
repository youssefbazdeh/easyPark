import parking from "../models/parking.js";

export function getAll(req, res) {
    parking
      .find({})
      .then(docs => {
          res.status(200).json(docs);
      })
      .catch(err => {
          res.status(500).json({ error: err });
      });
}


export function getAllParkingByIdUser(req, res) {
    parking
  .find({"user_id": req.params.user_id })

  .then(parking=> {
      res.status(200).json(parking);
  })
  .catch(err => {
      res.status(500).json({ error: err });
  });
}


export function updateParking(req, res) {
    parking.updateOne({ _id: req.params._id }, { $set: req.body })
    .then((doc) => {
      res.status(200).json("parking updated");
    })
    .catch((err) => {
      res.status(500).json({ error: err });
    });
}

export function  addParking(req, res) {
    parking
  .create({
    place: req.body.place,
    price: req.body.price,
    user_id: req.body.user_id
  })
    .then((newParking) => {
      res.status(201).json(newParking);
    })
    .catch((err) => {
      res.status(400).json(err);
    });
}

export function updataParking(req, res) {
    parking
      .findOneAndUpdate({ "_id":req.params.idparking },
          { "place": req.body.place
        })
      .then(doc => {
          res.status(200).json(doc);
      })
      .catch(err => {
          res.status(500).json({ error: err });
      });
}

export function getParkingById(req, res) {
    parking.findOne({ _id: req.params.id })
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
    parking
        .findOneAndRemove({ "_id": req.params.idparking })
        .then(doc => {
            res.status(200).json(doc);
        })
        .catch(err => {
            res.status(500).json({ error: err });
        });
  }