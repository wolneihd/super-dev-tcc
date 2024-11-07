// mensagem
export interface Mensagem {
  id: number;
  tipoMensagem: string;
  timestamp: number;
  textMsg: string;
  feedback: string;
  categoria: string;
  analise_ia: string;
}

// usuario
export interface Usuario {
  id: number;
  userId: number;
  firstName: string;
  lastName: string;
  mensagens: Mensagem[];
}
