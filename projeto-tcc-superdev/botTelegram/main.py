import telebot
import os
from dotenv import load_dotenv
from service_saveDB import send_user_data
import speech_recognition as sr
from pydub import AudioSegment

# Carregar variáveis de ambiente
load_dotenv()
TELEGRAM_TOKEN = os.getenv('TELEGRAM_TOKEN')

# Defina o caminho da pasta onde as imagens serão salvas
IMAGE_SAVE_PATH = 'images/'

# Certifique-se de que a pasta existe
if not os.path.exists(IMAGE_SAVE_PATH):
    os.makedirs(IMAGE_SAVE_PATH)

# Criar uma instância do bot
bot = telebot.TeleBot(TELEGRAM_TOKEN)

# Mensagem handler    
def verificar(mensagem):
    return True

@bot.message_handler(func=verificar)
def responder(mensagem):
    send_user_data(mensagem)
    print(mensagem.from_user.id)
    print(mensagem.from_user.first_name)
    print(mensagem.from_user.last_name)
    print(mensagem.from_user.first_name)
    print(mensagem.date)
    print(mensagem.json['text'])
    bot.send_message(mensagem.chat.id, "mensagem recebida, estaremos analisando.")

print("Bot Telegram rodando...")

if __name__ == '__main__':
    bot.polling(none_stop=True)