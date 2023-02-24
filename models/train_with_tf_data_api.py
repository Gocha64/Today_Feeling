import matplotlib.pyplot as plt
import numpy as np
import PIL
import tensorflow as tf

from tensorflow import keras
from tensorflow.keras import layers
from tensorflow.keras.models import Sequential
import pathlib

import math
import numpy as np
import pandas as pd
import glob

# import scikitplot
from matplotlib import pyplot as plt
import seaborn as sns

# from sklearn.model_selection import train_test_split
# from sklearn.preprocessing import LabelEncoder
# from sklearn.metrics import classification_report

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

batch_size = 32
img_height = 256
img_width = 256
img_depth = 3
num_classes = 6

data_dir = "/mnt/d/emo/한국인 감정인식을 위한 복합 영상/cropResizeTraining"

train_ds = tf.keras.utils.image_dataset_from_directory(
    data_dir,
    validation_split=0.2,
    subset="training",
    seed=123,
    image_size=(img_height, img_width),
    batch_size=batch_size)

val_ds = tf.keras.utils.image_dataset_from_directory(
    data_dir,
    validation_split=0.2,
    subset="validation",
    seed=123,
    image_size=(img_height, img_width),
    batch_size=batch_size)

class_names = train_ds.class_names
normalization_layer = tf.keras.layers.Rescaling(1./255)

normalized_ds = train_ds.map(lambda x, y: (normalization_layer(x), y))
image_batch, labels_batch = next(iter(normalized_ds))
first_image = image_batch[0]
# Notice the pixel values are now in `[0,1]`.
# print(np.min(first_image), np.max(first_image))

AUTOTUNE = tf.data.AUTOTUNE
train_ds = train_ds.cache().prefetch(buffer_size=AUTOTUNE)
val_ds = val_ds.cache().prefetch(buffer_size=AUTOTUNE)


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

optim = [
    optimizers.Nadam(
        learning_rate=0.001,
        beta_1=0.9,
        beta_2=0.999,
        epsilon=1e-07,
        name='Nadam'),
        
    optimizers.Adam(0.001),
]

model.compile(
    loss='sparse_categorical_crossentropy',
    optimizer='adam',
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

model_checkpoint = ModelCheckpoint(filepath="/mnt/d/emo/{epoch}-{val_loss: .2f}-{val_accuracy: .2f}.h5", monitor = "val_loss", save_best_only=True, verbose = 1)

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

epochs = 100
history = model.fit(
  train_ds,
#   steps_per_epoch = 33741 / batch_size,
  validation_data=val_ds,
  epochs=epochs,
  callbacks=callbacks,
  use_multiprocessing=True
)

model.save("/mnt/d/emo/model.h5")

sns.set()
fig = pyplot.figure(0, (12, 4))

ax = pyplot.subplot(1, 2, 1)
sns.lineplot(history.epoch, history.history['accuracy'], label='train')
sns.lineplot(history.epoch, history.history['val_accuracy'], label='valid')
pyplot.title('Accuracy')
pyplot.tight_layout()

ax = pyplot.subplot(1, 2, 2)
sns.lineplot(history.epoch, history.history['loss'], label='train')
sns.lineplot(history.epoch, history.history['val_loss'], label='valid')
pyplot.title('Loss')
pyplot.tight_layout()

pyplot.savefig('./epoch_history_efficientnet.png')