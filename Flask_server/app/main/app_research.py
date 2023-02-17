from datetime import datetime
from flask import g, jsonify, request
from models.Statistics import Statistics
from service.StatisticsQuery import select_statistics_with_userUID_Day,\
                                    select_statistics_with_userUID_Week,\
                                    select_statistics_with_userUID_Month,\
                                    select_statistics_with_userUID
from main import bp


@bp.route('/emotion/stat_day', methods = ["GET"])
def research_day():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})

    try:

        targetTime = datetime.now()

        date = request.args.get("date")
        if date != None:
            # print(date)
            targetTimestamp = int(date)
            targetTime = datetime.fromtimestamp(targetTimestamp)

        stats = select_statistics_with_userUID_Day(g.user.uid, targetTime)
        researchDict = countEmotion(stats)

        return jsonify({"result" : researchDict})

    except Exception as e:
        print(e.args)
        return jsonify({"result_f": "undefined error"})



@bp.route('/emotion/stat_month', methods = ['GET'])
def research_month():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})

    try:
        targetTime = datetime.now()

        date = request.args.get("date")
        if date != None:
            # print(date)
            targetTimestamp = int(date)
            targetTime = datetime.fromtimestamp(targetTimestamp)
            

        stats = select_statistics_with_userUID_Month( g.user.uid, targetTime)
        researchDict = countEmotion(stats)

        return jsonify({"result" : researchDict})

    except Exception as e:
        print(e.args)
        return jsonify({"result_f": "undefined error"})


@bp.route('/emotion/stat', methods = ['GET'])
def research_all():
    if g.user == None:
        return jsonify({"result_f" : "authentication failed"})

    try:
        stats = select_statistics_with_userUID(g.user.uid)
        researchDict = countEmotion(stats)

        return jsonify({"result" : researchDict})

    except Exception as e:
        print(e.args)
        return jsonify({"result_f": "undefined error"})



def countEmotion(stats):
    emotionList = [0,0,0,0,0,0]
    for s in stats:
        emotionList[s.emotion - 1] += 1

    researchDict = dict()
    researchDict['count'] = len(stats)
    researchDict['angerCount'] = emotionList[0]
    researchDict['fearCount'] = emotionList[1]
    researchDict['happinessCount'] = emotionList[2]
    researchDict['sadnessCount'] = emotionList[3]
    researchDict['surpriseCount'] = emotionList[4]
    return researchDict 

