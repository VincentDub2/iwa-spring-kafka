package service_messaging.dto;

public class MessageEvent {

        private Long conversationId;
        private Long senderId;
        private Long receiverId;
        private String contenu;

        public MessageEvent() {
        }

        public MessageEvent(Long conversationId, Long senderId, String contenu) {
            this.conversationId = conversationId;
            this.senderId = senderId;
            this.contenu = contenu;
        }

        public Long getConversationId() {
            return conversationId;
        }

        public void setConversationId(Long conversationId) {
            this.conversationId = conversationId;
        }

        public Long getSenderId() {
            return senderId;
        }

        public void setSenderId(Long senderId) {
            this.senderId = senderId;
        }

        public String getContenu() {
            return contenu;
        }

        public void setContenu(String contenu) {
            this.contenu = contenu;
        }

        public Long getReceiverId() {
            return receiverId;
        }

        public void setReceiverId(Long receiverId) {
            this.receiverId = receiverId;
        }

        @Override
        public String toString() {
            return "MessageEvent{" +
                    "conversationId=" + conversationId +
                    ", senderId=" + senderId +
                    ", contenu='" + contenu + '\'' +
                    '}';
        }

}
