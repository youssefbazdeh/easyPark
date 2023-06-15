import express, { json, urlencoded } from 'express';
import { connect } from 'mongoose';
import cors from 'cors';
import morgan from 'morgan';
import userRoutes from './routes/user.js';
import vehicletRoutes from './routes/vehicle.js';
import parkingRoutes from './routes/parking.js';

const app = express();

const port = process.env.PORT || 9000;


const database = 'malek'
connect(`mongodb://localhost:27017/${database}`)
    .then(() => console.log("connected"))
    .catch((error) => console.log(error));

app.use(cors());
app.use(morgan("dev"));
app.use(json()); // Pour analyser (parsing) les requetes application/json
app.use(urlencoded({ extended: true }));

app.use('/user', userRoutes);
app.use('/vehicle', vehicletRoutes);
app.use('/parking',parkingRoutes);

app.use((req, ers, next) => {
    console.log("middleware just ran");
    next();
});

app.use("/gse", (req, ers, next) => {
    console.log("middleware just ran on a gse route");
    next();
});

// pr�fixe chaque route ici avec /game


app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}/`);
});