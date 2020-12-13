# AstroTalkAssignment



Social Distance Assignment APIS

API

1. In order to create profile use this api 
API TYPE - POST
Parameters - name, email, password
http://192.187.126.18:8083/home/createProfile?name=prem&email=prem@g.com&password=123

Response 

{
    "msgType": 0,
    "Message": "Profile Added Successfully"
}


2. In order to Add friends in the friend List
API TYPE - POST
Parameters - userName, friendName
http://192.187.126.18:8083/home/addFriend?userName=ayush&friendName=preeti

Response

{
    "msgType": 0,
    "Message": "Friend added Successsfully to friendList"
}


3. In order to view Friends in the freind list 
API TYPE - POST
Parameters - userName
http://192.187.126.18:8083/home/viewFriendList?userName=ayush

Response

{
    "msgType": 0,
    "Friend List": [
        "preeti"
    ]
}



4. In order to remove friend in the friendList
API TYPE - POST
Parameters - userName, friendToRemove
http://192.187.126.18:8083/home/removeFriend?userName=ayush&friendToRemove=ram

Response

{
    "msgType": 0
	 "Message": "Friend removed Successsfully to friendList"
}
