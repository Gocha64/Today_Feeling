from flask import render_template, request, redirect, url_for, g, jsonify
from service.UserQuery import insert_user, select_user_with_id, select_user_with_email
from service.UserdataQuery import insert_userdata
from models.User import User
from models.Userdata import Userdata
import hashlib
from main import bp


@bp.route('/member/register', methods=["GET", "POST"])
def register():
    if request.method == "GET":
        #이미 로그인 했다면 메인페이지
        if g.user != None:
            return redirect(url_for("hello"))

        return render_template("register.html")

    if request.method == "POST":
        #bcrypt = Bcrypt(app)

        get_json_data = request.get_json(True)
        #print(get_json_data)

        userId = get_json_data['userId']
        userPw = get_json_data['userPw']
        userName = get_json_data['userName']
        userSex = get_json_data['userSex']
        email = get_json_data['userEmail']
        anger = get_json_data['anger']
        fear = get_json_data['fear']
        happiness = get_json_data['happiness']
        sadness = get_json_data['sadness']
        surprise = get_json_data['surprise']


        

        #print(userId, userPw, userName, userSex)


        # sha1으로 해싱됨
        userPwHash = str(hashlib.sha1(userPw.encode('utf-8')).hexdigest())
        #userPwHash = bcrypt.generate_password_hash(userPw)

        user = User(userId, userPwHash, userName, userSex, email)
        
        if select_user_with_id(userId) != None:
            return jsonify({'result' : 'overlaped ID'})
        
        elif select_user_with_email(email) != None:
            return jsonify({'result' : 'overlaped Email'})
        

        else:
            try:
                insert_user(user)
                userUid = select_user_with_id(userId)
                userdata = Userdata(userUid.uid, anger, fear, happiness, sadness, surprise)
                insert_userdata(userdata)
                print(f"{userId} registered")
                return jsonify({'result' : 'success'})
            except Exception as e:
                print(e.args)
                return jsonify({'result' : 'undefiend Error'})

    
    
