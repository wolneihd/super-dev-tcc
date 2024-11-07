import requests
import json
from groqIA import analise_texto_gropIA
from entidades import AnaliseIA

def save_message_DB(mensagem, audio_transcrito=None):

    url = "http://localhost:8081/dados/nova-mensagem"

    if (mensagem.content_type == "text"):
        textoMensagem = mensagem.json['text']
        analise = analise_texto_gropIA(mensagem.json['text'])
    elif (mensagem.content_type == "voice"):
        textoMensagem = audio_transcrito
        analise = analise_texto_gropIA(audio_transcrito)
    elif (mensagem.content_type == "photo"):
        textoMensagem = "imagem"
        analise = AnaliseIA(None, None, None)
        

    data = {
        "userId": mensagem.from_user.id,
        "firstName": mensagem.from_user.first_name,
        "lastName": mensagem.from_user.last_name,
        "mensagens": [
            {
                "tipoMensagem": mensagem.content_type,
                "timestamp": mensagem.date,
                "textMsg": textoMensagem,
                "categoria": analise.categoria,
                "analise_ia": analise.analise_ia,
                "feedback": analise.feedback
            }
        ]
    }

    headers = {
        "Content-Type": "application/json"  # Especifica que o corpo da requisição é JSON
    }

    # Fazendo a requisição POST
    response = requests.post(url, headers=headers, data=json.dumps(data))

    # Verificando o status da resposta
    if response.status_code == 200:
        print("Dados enviados com sucesso:", response.json())
    else:
        print("Erro ao enviar dados:", response.status_code, response.text)