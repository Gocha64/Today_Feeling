from flask import Flask, render_template, request, redirect, url_for, g, jsonify
from flask_bcrypt import Bcrypt
import User
import Userdata
import hashlib

from __main__ import app


@app.route('/member/modify/info', methods=["GET", "POST"])
def modify():
    if request.method == "GET":
        if g.user == None:
            return redirect(url_for("hello"))
        return render_template("modify.html", userId = g.user.id)

    if request.method == "POST":
    # bcrypt = Bcrypt(app)
        if g.user == None:
            return jsonify({"result" : "authentication failed"})

        get_json_data = request.get_json(True)
        # print(get_json_data)

        userID = g.user.id
        userPw = get_json_data['userPw']
        userName = get_json_data['userName']
        userSex = get_json_data['userSex']
        userEmail = get_json_data['userEmail']

        # print(userId, userPw, userName, userSex)

        # sha1으로 해싱됨
        userPwHash = str(hashlib.sha1(userPw.encode('utf-8')).hexdigest())
        # userPwHash = bcrypt.generate_password_hash(userPw)

        userModi = User.User(userID, userPwHash, userName, userSex, userEmail)
        # print(userModi)

        try:
            User.update_user_with_uid(g.user.uid, userModi)
            print("userinfo changed")
            return jsonify({'result': 'success'})
        except Exception as e:
            print(e.args)
            return jsonify({'result': 'undefined Error'})
            



@app.route('/member/modify/genre', methods=["GET", "POST"])
def modify_genre():
    if request.method == "GET":
        if g.user == None:
            return redirect(url_for("hello"))
        return render_template("modify_genre.html", userId = g.user.id)

    if request.method == "POST":
    # bcrypt = Bcrypt(app)
        if g.user == None:
            return jsonify({"result" : "authentication failed"})
        
        get_json_data = request.get_json(True)
        # print(get_json_data)

        anger = get_json_data['anger']
        fear = get_json_data['fear']
        happiness = get_json_data['happiness']
        sadness = get_json_data['sadness']
        surprise = get_json_data['surprise']

        userdataModi = Userdata.Userdata(g.user.uid, anger, fear, happiness, sadness, surprise)

        print(userdataModi)
        try:
            Userdata.update_userdata_with_uid(g.user.uid, userdataModi)
            print("userdata changed")
            return jsonify({'result': 'success'})
        except Exception as e:
            print(e.args)
            return jsonify({'result': 'undefined Error'})



