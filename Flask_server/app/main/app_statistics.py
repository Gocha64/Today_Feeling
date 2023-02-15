from flask import g, jsonify, request, current_app
import service.song_recommendation as song_recommendation
from models.Statistics import Statistics
from service.StatisticsQuery import insert_statistics,\
                                    select_statistics_with_userUID_Day,\
                                    select_statistics_with_userUID_Week,\
                                    select_statistics_with_userUID_Month,\
                                    select_statistics_with_userUID
from service.PlaylistQuery import get_songUrl_with_uid
from main import bp



@bp.route('/emotion/recommend', methods = ["POST"])
def emotion_recommend():
    if g.user == None:
        return jsonify({"result" : "authentication failed"})

    try:
        get_json_data = request.get_json(True)

        emotion_data = get_json_data['emotion_data']


        song = song_recommendation.song_recommend(emotion_data, g.user.uid)

        stat = Statistics(g.user.uid, song.uid, emotion_data)
        insert_statistics(current_app, stat)

        return jsonify({"result" : song.urlWeb})

    except Exception as e:
        print(e.args)
        return jsonify({"result" : "undefined error"})
    

@bp.route('/emotion/info_day', methods = ["GET"])
def emotion_day():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})

    try:
        stats = select_statistics_with_userUID_Day(current_app, g.user.uid)
        statList = [s.toDict() for s in stats]
        statList = include_songUrl(statList)

        return jsonify({"result" : statList})

    except Exception as e:
        print(e.args)
        return jsonify({"result_f": "undefined error"})


@bp.route('/emotion/info_week', methods = ['GET'])
def emotion_week():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})

    try:
        stats = select_statistics_with_userUID_Week(current_app, g.user.uid)
        statList = [s.toDict() for s in stats]
        statList = include_songUrl(statList)

        return jsonify({"result" : statList})

    except Exception as e:
        print(e.args)
        return jsonify({"result_f": "undefined error"})


@bp.route('/emotion/info_month', methods = ['GET'])
def emotion_month():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})

    try:
        stats = select_statistics_with_userUID_Month(current_app, g.user.uid)
        statList = [s.toDict() for s in stats]
        statList = include_songUrl(statList)

        return jsonify({"result" : statList})

    except Exception as e:
        print(e.args)
        return jsonify({"result_f": "undefined error"})


@bp.route('/emotion/info', methods = ['GET'])
def emotion_info():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})

    try:
        stats = select_statistics_with_userUID(current_app, g.user.uid)
        statList = [s.toDict() for s in stats]
        statList = include_songUrl(statList)

        return jsonify({"result" : statList})

    except Exception as e:
        print(e.args)
        return jsonify({"result_f": "undefined error"})


# 데이터 전송을 위한 dict 가공
def include_songUrl(statList):
    staDict = dict()

    for stat in statList:
        songUrl = get_songUrl_with_uid(current_app, stat['songUID'])
        stat['songUrl'] = songUrl
        stat.pop('songUID')
        

    return statList
