from flask import g, jsonify, request
import Statistics
import json
import song_recommendation
import Playlist
from __main__ import app



@app.route('/emotion/recommend', methods = ["POST"])
def emotion_recommend():
    if g.user == None:
        return jsonify({"result" : "authentication failed"})

    try:
        get_json_data = request.get_json(True)

        emotion_data = get_json_data['emotion_data']


        song = song_recommendation.song_recommend(emotion_data)

        stat = Statistics.Statistics(g.user.uid, song.uid, emotion_data)
        Statistics.insert_statistics(stat)

        return jsonify({"result" : song.urlWeb})

    except Exception as e:
        print(e.args)
        return jsonify({"result" : "undefined error"})
    

@app.route('/emotion/info_day', methods = ["GET"])
def emotion_day():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})
    
    stats = Statistics.select_statistics_with_userUID_Day(g.user.uid)
    statList = [s.toDict() for s in stats]
    statList = include_songUrl(statList)

    return jsonify({"result" : statList})
    

@app.route('/emotion/info_week', methods = ['GET'])
def emotion_week():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})
    
    stats = Statistics.select_statistics_with_userUID_Week(g.user.uid)
    statList = [s.toDict() for s in stats]
    statList = include_songUrl(statList)

    return jsonify({"result" : statList})
    

@app.route('/emotion/info_month', methods = ['GET'])
def emotion_month():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})
    
    stats = Statistics.select_statistics_with_userUID_Month(g.user.uid)
    statList = [s.toDict() for s in stats]
    statList = include_songUrl(statList)

    return jsonify({"result" : statList})


@app.route('/emotion/info', methods = ['GET'])
def emotion_info():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})
    
    stats = Statistics.select_statistics_with_userUID(g.user.uid)
    statList = [s.toDict() for s in stats]
    statList = include_songUrl(statList)

    return jsonify({"result" : statList})


# 데이터 전송을 위한 dict 가공
def include_songUrl(statList):
    staDict = dict()

    for stat in statList:
        songUrl = Playlist.get_songUrl_with_uid(stat['songUID'])
        stat['songUrl'] = songUrl
        stat.pop('songUID')
        

    return statList
