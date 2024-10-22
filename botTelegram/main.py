import os
import telebot
import speech_recognition as sr
from pydub import AudioSegment
from dotenv import load_dotenv

from service_saveDB import save_text_message_DB, save_audio_message_DB

# Carregar variáveis de ambiente
load_dotenv()
TELEGRAM_TOKEN = os.getenv('TELEGRAM_TOKEN')
bot = telebot.TeleBot(TELEGRAM_TOKEN)


@bot.message_handler(commands=['start'])
def start_message(message):
    bot.reply_to(message, "Olá, você está no IA-Feedback-Analyser!")


@bot.message_handler(content_types=['text'])
def handle_text(message):
    save_text_message_DB(message)
    bot.send_message(message.chat.id, "mensagem recebida, estaremos analisando.")


@bot.message_handler(content_types=['voice'])
def handle_voice(message):
    # Baixa o arquivo de áudio
    file_info = bot.get_file(message.voice.file_id)
    downloaded_file = bot.download_file(file_info.file_path)

    # Salva o arquivo OGG
    ogg_file_path = 'audio.ogg'
    with open(ogg_file_path, 'wb') as new_file:
        new_file.write(downloaded_file)

    # Converte OGG para WAV
    wav_file_path = 'audio.wav'
    convert_audio(ogg_file_path, wav_file_path)

    # Transcreve o áudio
    transcription = transcribe_audio(wav_file_path)

    # Salva informação no banco de dados
    save_audio_message_DB(message, transcription)
    bot.send_message(message.chat.id, "mensagem recebida, estaremos analisando.")


def convert_audio(input_file, output_file):
    audio = AudioSegment.from_ogg(input_file)
    audio.export(output_file, format="wav")


def transcribe_audio(audio_path):
    recognizer = sr.Recognizer()
    
    with sr.AudioFile(audio_path) as source:
        audio_data = recognizer.record(source)

    try:
        # Transcreve usando a API do Google
        return recognizer.recognize_google(audio_data, language='pt-BR')
    except sr.UnknownValueError:
        return "Não consegui entender o áudio."
    except sr.RequestError as e:
        return f"Erro ao conectar ao serviço de reconhecimento de fala: {e}"


if __name__ == '__main__':
    print("bot start...")
    bot.polling(none_stop=True)
