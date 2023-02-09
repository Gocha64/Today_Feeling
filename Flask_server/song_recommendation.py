import Playlist


def song_recommend(emotion_data):
    # playlist의 오브젝트를 반환
    # 임시로 playlist의 첫번째 요소를 반환
    # 알고리즘 추가해야함
    
    if emotion_data:
        resultSong = Playlist.select_playlist()[0]
        return resultSong
    return None