from datetime import timedelta
from flask import Flask, render_template, request, redirect, url_for, g, jsonify, session
from dotenv import load_dotenv
import os

app = Flask(__name__)

load_dotenv()
app.secret_key = os.urandom(24)

import app_register
import app_login

#app.config["PERMANENT_SESSION_LIFETIME"] = timedelta(minutes=5)
app.config['SESSION_PERMANENT'] = True




@app.route('/')
def hello():
    
    if g.user is not None:
        return render_template("index.html", userName = g.user.name)
    return render_template("index.html")

@app.route('/redirectTest')
def redirectTest():
    return redirect(url_for('hello'))


@app.errorhandler(404)
def page_not_found(error):
    return render_template('404.html')

@app.route("/helloTest")
def helloTest():
    
    return jsonify({"result" : "helloTest"})


if __name__ == "__main__":
    app.run(debug=True)



