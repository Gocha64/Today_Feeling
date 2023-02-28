import math
import numpy as np
import pandas as pd
import glob

import scikitplot
import seaborn as sns
from matplotlib import pyplot as plt

from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from sklearn.metrics import classification_report

import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import optimizers
from tensorflow.keras.datasets import mnist
from tensorflow.keras.models import Sequential, Model
from tensorflow.keras.layers import Flatten, Dense, Conv2D, MaxPooling2D, Input, GlobalAveragePooling2D
from tensorflow.keras.layers import Dropout, BatchNormalization, LeakyReLU, Activation
from tensorflow.keras.callbacks import Callback, EarlyStopping, ReduceLROnPlateau, ModelCheckpoint
from tensorflow.keras.preprocessing.image import ImageDataGenerator

from keras.utils import np_utils
from tensorflow.keras.applications.efficientnet import EfficientNetB0

import wandb
from wandb.keras import WandbCallback

def loss_accu(history):
    sns.set()
    fig = plt.figure(0, (12, 4))

    ax = plt.subplot(1, 2, 1)
    sns.lineplot(history.epoch, history.history['accuracy'], label='train')
    sns.lineplot(history.epoch, history.history['val_accuracy'], label='valid')
    plt.title('Accuracy')
    plt.tight_layout()

    ax = plt.subplot(1, 2, 2)
    sns.lineplot(history.epoch, history.history['loss'], label='train')
    sns.lineplot(history.epoch, history.history['val_loss'], label='valid')
    plt.title('Loss')
    plt.tight_layout()

    plt.savefig('epoch_history_dcnn.png')
    plt.show()

def train():
    # train, test 
    # X = np.load("X.npy")
    # y = np.load("y.npy")


    X_train, X_valid, y_train, y_valid = train_test_split(
        X,
        y,
        shuffle=True, 
        stratify=y,
        test_size=0.2, 
        random_state=42
    )

    X_train = X_train / 255.
    X_valid = X_valid / 255.

    img_width = X_train.shape[1]
    img_height = X_train.shape[2]
    img_depth = X_train.shape[3]
    num_classes = 6

    #batch size of 32 performs the best.
    batch_size = 32 
    epochs = 100
    optim = [
        optimizers.Nadam(
            learning_rate=0.001,
            beta_1=0.9,
            beta_2=0.999,
            epsilon=1e-07,
            name='Nadam'),
            
        optimizers.Adam(0.001),
    ]

    # build model
    base_model = EfficientNetB0(input_shape = (img_width, img_height, img_depth), include_top = False, weights=None)
    bm_output = base_model.output
    output_layer = GlobalAveragePooling2D()(bm_output)
    output_layer = Dense(128, activation='relu')(output_layer)
    output_layer = BatchNormalization()(output_layer)
    output_layer = Dropout(0.6)(output_layer)
    output_layer = Dense(50, activation='relu')(output_layer)
    output_layer = Dense(num_classes, activation='softmax')(output_layer)
    model = Model(inputs=base_model.input, outputs=output_layer)

    model.compile(
        loss='categorical_crossentropy',
        optimizer=optim,
        metrics=['accuracy']
    )
    
    # call back
    early_stopping = EarlyStopping(
    monitor='val_accuracy',
    min_delta=0.00005,
    patience=20,
    verbose=1,
    restore_best_weights=True,
    )

    model_checkpoint = ModelCheckpoint(filepath="./workdir/{epoch}-{val_loss: .2f}-{val_accuracy: .2f}.h5", monitor = "val_loss", save_best_only=True, verbose = 1)

    lr_scheduler = ReduceLROnPlateau(
        monitor='val_accuracy',
        factor=0.5,
        patience=7,
        min_lr=1e-7,
        verbose=1,
    )

    callbacks = [
        early_stopping,
        lr_scheduler,
        model_checkpoint
    ]

    # data augmentation
    train_datagen = ImageDataGenerator(
        rotation_range=15, # 0~10도로 회전
        width_shift_range=0.15, # 왼쪽 오른쪽으로 이동
        height_shift_range=0.15, # 위, 아래로 이동
        shear_range=0.15, # 0.15 라디안 내외로 시계반대 방향으로 기울기
        horizontal_flip=True, # 좌, 우 대칭
        brightness_range = [0.2, 1.0], # 랜덤하게 밝기 
    )

    train_datagen.fit(X_train)


    history = model.fit_generator(
        train_datagen.flow(X_train, y_train, batch_size=batch_size),
        validation_data=(X_valid, y_valid),
        steps_per_epoch=len(X_train) / batch_size,
        epochs=epochs,
        callbacks=callbacks,
        use_multiprocessing=True
    )

    loss_accu(history)
    model.save("model_final.h5")