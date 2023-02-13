from flask import g, jsonify
import User
import Userdata

from __main__ import app

# 회원정보 조회 api
@app.route("/member/search", methods = ["GET"])
def member_search():
    if g.user == None:
        return jsonify({"result" : "authentication failed"})
    
    userDict = g.user.toDict_without_password()
    userdataDict = Userdata.select_userdata_with_uid(g.user.uid).toDict()

    if userdataDict != None:
        userDict.update(userdataDict)

    return jsonify(userDict)
    

    
