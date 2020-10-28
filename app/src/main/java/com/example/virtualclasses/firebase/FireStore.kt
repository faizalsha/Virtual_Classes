package com.example.virtualclasses.firebase

import com.example.virtualclasses.local.Constants
import com.example.virtualclasses.model.*
import com.example.virtualclasses.utils.Utility
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

object FireStore {

    private val mFireStoreRef: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun addUserToSystem(name: String, email: String , password: String, listener: (Boolean)->Unit){
        FireAuth.registerUser(email, password){ result: Any,  successful: Boolean ->
            //save user in firestore
            if(successful && result is AuthResult){
                val student = Student(name, email, null, null, false)
                saveUserDetailsToFireStore(student, result.user!!.uid)
                listener(true)
            }else{
                //show toast
                listener(false)
            }
        }
    }
    private fun saveUserDetailsToFireStore(student: Student, uid: String){
        mFireStoreRef.collection(Constants.USERS).document(uid)
            .set(student)
    }

    fun checkUserExistInFireStore(uid: String, listener: (Boolean) -> Unit){
        mFireStoreRef.collection(Constants.USERS).document(uid).get().addOnSuccessListener {
            if (it.exists()){
                listener(true)
            }else{
                listener(false)
            }
        }.addOnFailureListener {
            listener(false)
        }
    }

    fun getMyAllRooms(userId: String, listener: (MutableList<RoomDetails>?) -> Unit){
        mFireStoreRef.collection(Constants.USERS)
            .document(userId)
            .collection(Constants.ROOMS)
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(RoomDetails::class.java)
                listener(data)
            }.addOnFailureListener {
                listener(null)
            }
    }

    fun createRoom(roomDetails: RoomDetails, userId: String, listener: (Boolean)->Unit){
        val roomRef = mFireStoreRef.collection(Constants.USERS)
            .document(userId)
            .collection(Constants.ROOMS).document()

        roomDetails.roomId = roomRef.id

         roomRef.set(roomDetails).addOnSuccessListener {
                listener(true)
            }.addOnFailureListener {
                listener(false)
            }
    }

    fun subscribe(roomInfo: RoomInfo, listener: (Boolean) -> Unit) {
        mFireStoreRef.collection(Constants.USERS)
            .document(roomInfo.ownerId)
            .collection(Constants.ROOMS)
            .document(roomInfo.roomId)
            .get().addOnSuccessListener {
                if(!it.exists()){
                    listener(false)
                    return@addOnSuccessListener
                }
                mFireStoreRef.collection(Constants.USERS)
                    .document(FireAuth.getCurrentUser()!!.uid)
                    .collection(Constants.SUBSCRIBED_ROOM).add(roomInfo).addOnSuccessListener {
                        listener(true)
                    }.addOnFailureListener {
                        listener(false)
                    }
            }.addOnFailureListener {
                listener(false)
            }
    }

    fun getSubscribedRooms(userId: String, listener: (MutableList<RoomInfo>?)->Unit){
        mFireStoreRef.collection(Constants.USERS)
            .document(userId)
            .collection(Constants.SUBSCRIBED_ROOM)
            .get()
            .addOnSuccessListener {
                if(it.isEmpty){
                    listener(null)
                    return@addOnSuccessListener
                }
                listener(it.toObjects(RoomInfo::class.java))
            }.addOnFailureListener {
                listener(null)
            }
    }
    fun saveDefaultDaySchedule(dayOfWeekIndex: Int, defaultDaySchedule: DefaultDaySchedule, roomId: String, listener: (Boolean) -> Unit){
        var dayOfWeek = WeekDay.MONDAY.toString()
        when(dayOfWeekIndex){
            0 -> dayOfWeek = WeekDay.MONDAY.toString()
            1 -> dayOfWeek = WeekDay.TUESDAY.toString()
            2 -> dayOfWeek = WeekDay.WEDNESDAY.toString()
            3 -> dayOfWeek = WeekDay.THURSDAY.toString()
            4 -> dayOfWeek = WeekDay.FRIDAY.toString()
            5 -> dayOfWeek = WeekDay.SATURDAY.toString()
            6 -> dayOfWeek = WeekDay.SUNDAY.toString()
        }
        //todo: if current user is null send user to login screen and clear shared preferences
        val userId = FireAuth.getCurrentUser()!!.uid
        mFireStoreRef.collection(Constants.USERS)
            .document(userId)
            .collection(Constants.ROOMS)
            .document(roomId)
            .collection(Constants.DEFAULT)
            .document(dayOfWeek)
            .set(defaultDaySchedule).addOnSuccessListener {
                listener(true)
            }.addOnFailureListener {
                listener(false)
            }
    }
    fun getDefaultDaySchedule(dayOfWeekIndex: Int, roomId: String, listener: (DefaultDaySchedule?) -> Unit){
        val userId = FireAuth.getCurrentUser()?.uid ?: return
        if(!Utility.indexToWeekDay.containsKey(dayOfWeekIndex)) return
        val dayOfWeek = Utility.indexToWeekDay[dayOfWeekIndex].toString()
//        when(dayOfWeekIndex){
//            0 -> dayOfWeek = WeekDay.MONDAY.toString()
//            1 -> dayOfWeek = WeekDay.TUESDAY.toString()
//            2 -> dayOfWeek = WeekDay.WEDNESDAY.toString()
//            3 -> dayOfWeek = WeekDay.THURSDAY.toString()
//            4 -> dayOfWeek = WeekDay.FRIDAY.toString()
//            5 -> dayOfWeek = WeekDay.SATURDAY.toString()
//            6 -> dayOfWeek = WeekDay.SUNDAY.toString()
//        }
        mFireStoreRef.collection(Constants.USERS)
            .document(userId)
            .collection(Constants.ROOMS)
            .document(roomId)
            .collection(Constants.DEFAULT)
            .document(dayOfWeek)
            .get().addOnSuccessListener {
                try {
                    val daySchedule = it.toObject(DefaultDaySchedule::class.java)
                    listener(daySchedule)
                }catch (e: Exception){
                    listener(null)
                }
            }.addOnFailureListener {
                listener(null)
            }
    }
}