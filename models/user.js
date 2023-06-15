import mongoose from 'mongoose';
const { Schema, model } = mongoose;

const userSchema = new Schema(
    {
        username: {
            type: String,
            unique: true,
            required: false
        },
        email: {
            type: String,
            unique: false,
            required: false,
        },
        password: {
            type: String,
            required: false,
            max: 100
        }
    },
        {
        timestamps:true
        }
);


export default model("User", userSchema);