import mongoose from 'mongoose';
const { Schema, model } = mongoose;

const vehicleSchema = new Schema(
    {
        license: {
            type: String,
            unique: false,
            required: false
        },
        nickname: {
            type: String,
            unique: false,
            required: false,
        },
        ownership: {
            type: String,
            unique: false,
            required: false,
        },
        user_id: {
            type: Schema.Types.ObjectId,
            ref: "users"
        }
    },
        {
        timestamps:true
        }
);


export default model("Vehicle", vehicleSchema);