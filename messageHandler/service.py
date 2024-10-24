import requests
import json
from groqIA import analise_texto_gropIA

def save_message_DB(mensagem, audio_transcrito=None):
    # analise = analise_texto_gropIA(mensagem.json['text'])
    global analise
    url = "http://localhost:8081/dados/nova-mensagem"

    #Define o tipo e o id da mensagem com base no tipo fornecido
    if mensagem.content_type == "text":
        analise = analise_texto_gropIA(mensagem.json['text'])
        tipos_mensagem = {
            "id": 1,
            "tipo": "texto"
        }
        msg = mensagem.json['text']
    elif mensagem.content_type == "image":
        tipos_mensagem = {
            "id": 2,
            "tipo": "imagem"
        }
        msg = "WIP - descrever imagem."
    elif mensagem.content_type == "voice":
        analise = analise_texto_gropIA(audio_transcrito)
        tipos_mensagem = {
            "id": 3,
            "tipo": "audio"
        }
        msg = audio_transcrito

    data = [
        {
            "userId": mensagem.from_user.id,
            "firstName": mensagem.from_user.first_name,
            "lastName": mensagem.from_user.last_name,
            "mensagens": [
                {
                    "id": 1,
                    "tiposMensagem": tipos_mensagem,
                    "timestampCod": mensagem.date,
                    "textMsg": msg,
                    "categoria": analise.categoria,
                    "analise_ia": analise.analise_ia,
                    "feedback": analise.feedback,
                }
            ]
        }
    ]

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