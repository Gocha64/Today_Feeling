from flask import Flask, render_template, request, redirect, url_for, g, jsonify
from dotenv import load_dotenv
import os


app = Flask(__name__)

load_dotenv()
app.secret_key = os.getenv("APP_SECRETKEY")

import app_register
import app_login

@app.route('/')
def hello():
    
    if g.user is not None:
        return render_template("index.html", userName = g.user.user_name)
    return render_template("index.html")

@app.route('/redirectTest')
def redirectTest():
    return redirect(url_for('hello'))


if __name__ == "__main__":
    app.run(debug=True)



