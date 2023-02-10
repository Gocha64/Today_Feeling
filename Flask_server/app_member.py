from flask import Flask, g, jsonify
from flask_bcrypt import Bcrypt
import User
import hashlib

from __main__ import app

# 회원정보 조회 api
@app.route("/member/search", methods = ["GET"])
def member_search():
    if g.user == None:
        return jsonify({"result" : "authentication failed"})
    
    userDict = g.user.toDict_without_password()
    return jsonify(userDict)
    

    
