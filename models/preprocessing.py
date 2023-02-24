import numpy as np
import pandas as pd
import glob
from matplotlib import pyplot as plt
import os
import cv2
from PIL import Image

def image_to_numpy():
    file_path = glob.glob("/mnt/d/emo/한국인 감정인식을 위한 복합 영상/cropResizeTraining/*/*.jpg")

    X = np.array([plt.imread(file_path[i]) for i in range(len(file_path))], dtype = object)
    y = np.array([file_path[i].split('/')[-2] for i in range(len(file_path))], dtype = object)

    return X, y

# 종속 = [0, 1, 2, 3, 4, 5]

def plus_pop(x):
    emotion_label_to_text = {
      "happiness" : 0, # happiness
      "surprice" : 1, # surprice
      "anger" : 2, # anger
      "fear" : 3, # fear
      "sadness" : 4, # sadness
      "neutral" : 5, # neutral
    } 
    return emotion_label_to_text[x]

def dependency_to_pandas(y):
    df = pd.DataFrame(y, columns = ["emotion"])

    df['emotion'] = df['emotion'].apply(plus_pop)
    y = df["emotion"].to_numpy()
    return y

def save(X, y):
    np.save("/mnt/d/emo/한국인 감정인식을 위한 복합 영상/cropTraining/X", X)
    np.save("/mnt/d/emo/한국인 감정인식을 위한 복합 영상/cropTraining/y", y)

def resize():
    category = ["anger", "happiness", "fear", "neutral", "sadness", "surprise"]
    for i in category:
        file_path = glob.glob(f"/mnt/d/emo/한국인 감정인식을 위한 복합 영상/cropTraining/{i}/*.jpg")
        k = 0
        for j in file_path:
            k += 1
            img = cv2.imread(j)
            img_resize = cv2.resize(img, dsize = (256, 256))
            img_resize = Image.fromarray(img_resize)
            img_resize = img_resize.convert('RGB')
            img_resize.save(f"/mnt/d/emo/한국인 감정인식을 위한 복합 영상/cropResizeTraining/{i}/{i}{k}.jpg")

def main():
    X, y = image_to_numpy()
    y = dependency_to_pandas(y)
    print(X.shape, y.shape)
    save(X, y)


main()