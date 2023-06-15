import mongoose from 'mongoose';
const { Schema, model } = mongoose;

const parkingSchema = new Schema(
    {
        place: {
            type: String,
            unique: false,
            required: false
        },
        price: {
            type: String,
            unique: false,
            required: false
        },
        user_id: {
            type: Schema.Types.ObjectId,
            ref: "users"
        },
    },
        {
        timestamps:true
        }
);


export default model("Parking", parkingSchema);