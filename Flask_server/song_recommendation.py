import Playlist


def song_recommend(emotion_data):
    # playlist의 오브젝트를 반환
    # 임시로 playlist의 첫번째 요소를 반환
    # 알고리즘 추가해야함
    
    """
    1. 감정 받아옴
    2. 사용자의 해당 감정에 해당하는 장르를 가져옴
    3. 해당 장르의 노래 중 1개를 추천
    """
    if emotion_data:
        resultSong = Playlist.select_playlist()[0]
        return resultSong
    return None