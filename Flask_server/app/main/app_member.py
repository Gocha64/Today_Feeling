from flask import g, jsonify, current_app
from service.UserdataQuery import select_userdata_with_uid
from main import bp

# 회원정보 조회 api
@bp.route("/member/search", methods = ["GET"])
def member_search():
    if g.user == None:
        return jsonify({"result" : "authentication failed"})
    
    userDict = g.user.toDict_without_password()
    userdataDict = select_userdata_with_uid(current_app, g.user.uid).toDict()

    if userdataDict != None:
        userDict.update(userdataDict)

    return jsonify(userDict)
    

    
