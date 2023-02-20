
function Emotion() {

    return (
        < py-script >
            import math
            import numpy as np
            import pandas as pd
            from PIL import Image
            import cv2

            import seaborn as sns
            from matplotlib import pyplot as plt

            from sklearn.model_selection import train_test_split
            from sklearn.preprocessing import LabelEncoder
            from sklearn.metrics import classification_report

            import tensorflow as tf
            from tensorflow import keras
            from tensorflow.keras import optimizers
            from tensorflow.keras.datasets import mnist
            from tensorflow.keras.models import Sequential
            from tensorflow.keras.layers import Flatten, Dense, Conv2D, MaxPooling2D
            from tensorflow.keras.layers import Dropout, BatchNormalization, LeakyReLU, Activation
            from tensorflow.keras.callbacks import Callback, EarlyStopping, ReduceLROnPlateau, ModelCheckpoint
            from tensorflow.keras.preprocessing.image import ImageDataGenerator

            from keras.utils import np_utils

            path = "/content/drive/MyDrive/학교 계정/프로그래밍 언어 공부 자료/SW boostcamp/기업 프로젝트/3단계 팀 프로젝트/ML project/얼굴 표정 감정 인식/model.h5"

            img = cv2.imread("/content/happy.jpg", cv2.IMREAD_GRAYSCALE)

            plt.imshow(img)

            img.shape

            img = cv2.resize(255 - img, (48, 48))

            img = img / 255.0
            img.shape

            {/* emotion_label_to_text = {
                0: 'anger',
            1: 'fear',
            2: 'happiness',
            3: 'sadness',
            4: 'surprise',
            5: 'neutral'
            } */}

            model.predict(np.expand_dims(img, axis = 0))

            yhat_valid = model.predict(np.expand_dims(img, axis = 0))
            yhat_valid = yhat_valid.argmax(axis = -1)
            yhat_valid

            emotion_label_to_text[yhat_valid[0]]
        </py-script >
    );
}

export default Emotion;