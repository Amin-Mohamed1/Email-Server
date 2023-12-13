<template>
  <div>
    <h2 class="sent-title">Sent</h2>
    <transition-group name="fade" mode="out-in">
      <div v-for="email in sentEmails" :key="email.id" class="email-item">
        <div class="info">
          <div class="receiver">{{ email.receiver }}</div>
          <div class="date-time">{{ email.dateTime }}</div>
        </div>
        <div class="subject">{{ email.subject }}</div>
        <div class="body">
          <div v-if="!email.expanded" class="truncated-body">
            {{ truncateBody(email.body) }}
            <span v-if="shouldTruncate(email.body)">
              ... <span @click="expandBody(email)">See more</span>
            </span>
          </div>
          <div v-else>
            {{ email.body }}
          </div>
          <div v-if="hasAttachment(email.attachments)" class="attachment-section">
            <strong class="attachment-label">Attachments:</strong>
            <ul>
              <li v-for="(attachment, index) in email.attachments" :key="index">
                {{ getAttachmentIcon(attachment.type) }} <strong>{{ attachment.name }}</strong>
              </li>
            </ul>
          </div>
          <div v-else class="no-attachments">No attachments</div>
        </div>
        <div class="meta">
          <div class="priority">Priority: {{ email.priority }}</div>
          <button @click="deleteSentEmail(email.id)" class="delete-btn">Delete</button>
        </div>
      </div>
    </transition-group>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const sentEmails = ref([
  { id: 1, receiver: 'Albert.Einstein@gmail.com', subject: 'Meeting Tomorrow', body: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', attachments: [{ name: 'project_plan.png', type: 'png' }, { name: 'meeting_notes.docx', type: 'docx' }], priority: 'Medium', dateTime: '2023-01-02 2:30 PM'},
  { id: 2, receiver: 'cristiano.ronaldo@gmail.com', subject: 'Meeting Tomorrow', body: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', attachments: [{ name: 'project_plan.png', type: 'png' }, { name: 'meeting_notes.docx', type: 'docx' }], priority: 'Medium', dateTime: '2023-01-02 2:30 PM'},
  { id: 3, receiver: 'vinisius.junior@gmail.com', subject: 'Meeting Tomorrow', body: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', attachments: [{ name: 'project_plan.png', type: 'png' }, { name: 'meeting_notes.docx', type: 'docx' }], priority: 'Medium', dateTime: '2023-01-02 2:30 PM'},
  { id: 4, receiver: 'Mo.salah@gmail.com', subject: 'Meeting Tomorrow', body: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', attachments: [{ name: 'project_plan.png', type: 'png' }, { name: 'meeting_notes.docx', type: 'docx' }], priority: 'Medium', dateTime: '2023-01-02 2:30 PM'},
]);

const deleteSentEmail = (id) => {
  sentEmails.value = sentEmails.value.filter(email => email.id !== id);
};

const shouldTruncate = (body) => body.length > 120;
const truncateBody = (body) => shouldTruncate(body) ? body.slice(0, 120) : body;

const expandBody = (email) => {
  email.expanded = true;
};

const hasAttachment = (attachments) => attachments.length > 0;

const getAttachmentIcon = (type) => {
  if (type.toLowerCase() === 'pdf') {
    return '📄'; // PDF icon
  } else if (type.toLowerCase() === 'docx') {
    return '📃'; // DOCX icon
  } else if (type.toLowerCase() === 'jpg' || type.toLowerCase() === 'jpeg' || type.toLowerCase() === 'png') {
    return '📷'; // Image icon
  } else {
    return '📎'; // Default attachment icon
  }
};
</script>

<style scoped>
  .sent-title {
    font-size: 24px;
    margin-bottom: 20px;
    color: #333;
  }

  .email-item {
    border: 1px solid #ddd;
    padding: 15px;
    margin-bottom: 15px;
    background-color: #fff;
    border-radius: 8px;
    transition: transform 0.3s ease-in-out;
  }

  .email-item:hover {
    transform: scale(1.02);
  }

  .info {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .receiver {
    font-weight: bold;
    color: #007BFF;
  }

  .date-time {
    color: #888;
  }

  .subject {
    margin-top: 5px;
    font-size: 18px;
    color: #333;
  }

  .body {
    margin-top: 10px;
    color: #555;
  }

  .meta {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 10px;
  }

  .priority {
    font-weight: bold;
  }

  .delete-btn {
    background-color: #ff5555;
    color: #fff;
    border: none;
    padding: 8px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
  }

  .delete-btn:hover {
    background-color: #dd4444;
  }

  .truncated-body {
    overflow: hidden;
    text-overflow: ellipsis;
    max-height: 3em; 
    line-height: 1.5em;
  }

  .truncated-body span {
    cursor: pointer;
    color: #007BFF;
    margin-left: 5px;
  }

  .attachment-indicator {
    margin-left: 5px;
  }

  .attachment-section {
    margin-top: 10px;
  }

  .attachment-label {
    font-weight: bold;
  }

  .no-attachments {
    color: #888;
    margin-top: 10px;
  }

  ul {
    list-style: none;
    padding: 0;
  }
</style>
