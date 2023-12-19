<template>
  <div>
    <h2 class="inbox-title">Inbox</h2>
    <transition-group name="fade" mode="out-in">
      <div v-for="email in displayedEmails" :key="email.id" class="email-item" @click="expandBody(email)">
        <div class="info">
          <div class="sender">{{ email.sender }}</div>
          <div class="date-time">{{ email.dateTime }}</div>
        </div>
        <div class="subject">{{ email.subject }}</div>
        <div class="body">
          <div v-if="!email.expanded" class="truncated-body">
            {{ truncateBody(email.body) }}
            <span v-if="shouldTruncate(email.body)">
              ... <span @click.stop="expandBody(email)">See more</span>
            </span>
          </div>
          <div v-else>
            {{ email.body }}
            <span @click.stop="expandBody(email)">See less</span>
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
          <button @click.stop="deleteEmail(email.id)" class="delete-btn">Delete</button>
        </div>
      </div>
    </transition-group>
    <div class="pagination">
      <button @click="changePage('prev')" :disabled="currentPage === 1">Prev</button>
      <span>{{ currentPage }}</span>
      <button @click="changePage('next')" :disabled="currentPage === totalPages">Next</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';

const currentUserEmail = 'user@gmail.com';
const emailsPerPage = 5;

const fetchEmails = async () => {
  try {
    const response = await axios.get(`http://localhost/emails/${currentUserEmail}`);
    emails.value = response.data;
  } catch (error) {
    console.error('Error fetching emails:', error);
  }
};

const emails = ref([
  { id: 1, sender: 'Albert.Einstein@gmail.com', subject: 'Meeting Tomorrow', body: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. ...', attachments: [{ name: 'project_plan.png', type: 'png' }, { name: 'meeting_notes.docx', type: 'docx' }], priority: 'Medium', dateTime: '2023-01-02 2:30 PM'},
  { id: 2, sender: 'Robert.Oppenheimer@gmail.com', subject: 'Presentation Feedback', body: 'Your presentation was excellent!', dateTime: '2023-01-02 2:30 PM', priority: 'Medium', attachments: [{ name: 'feedback.pdf', type: 'pdf' }, { name: 'slides.pdf', type: 'pdf' }] },
  { id: 3, sender: 'Neils.Bohr@gmail.com', subject: 'Meeting Tomorrow', body: 'Hi, let\'s discuss the project tomorrow.', dateTime: '2023-01-01 10:00 AM', priority: 'High', attachments: [] },
  { id: 4, sender: 'Lionel.Messi@gmail.com', subject: 'Presentation Feedback', body: 'Your presentation was excellent!', dateTime: '2023-01-02 2:30 PM', priority: 'Medium', attachments: [] },
  { id: 1, sender: 'Albert.Einstein@gmail.com', subject: 'Meeting Tomorrow', body: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. ...', attachments: [{ name: 'project_plan.png', type: 'png' }, { name: 'meeting_notes.docx', type: 'docx' }], priority: 'Medium', dateTime: '2023-01-02 2:30 PM'},
  { id: 2, sender: 'Robert.Oppenheimer@gmail.com', subject: 'Presentation Feedback', body: 'Your presentation was excellent!', dateTime: '2023-01-02 2:30 PM', priority: 'Medium', attachments: [{ name: 'feedback.pdf', type: 'pdf' }, { name: 'slides.pdf', type: 'pdf' }] },
  { id: 3, sender: 'Neils.Bohr@gmail.com', subject: 'Meeting Tomorrow', body: 'Hi, let\'s discuss the project tomorrow.', dateTime: '2023-01-01 10:00 AM', priority: 'High', attachments: [] },
  { id: 4, sender: 'Lionel.Messi@gmail.com', subject: 'Presentation Feedback', body: 'Your presentation was excellent!', dateTime: '2023-01-02 2:30 PM', priority: 'Medium', attachments: [] },
]);

onMounted(() => {
  fetchEmails();
});

const currentPage = ref(1);

const totalPages = computed(() => Math.ceil(emails.value.length / emailsPerPage));

const displayedEmails = computed(() => {
  const startIndex = (currentPage.value - 1) * emailsPerPage;
  const endIndex = startIndex + emailsPerPage;
  return emails.value.slice(startIndex, endIndex);
});

const deleteEmail = (id) => {
  emails.value = emails.value.filter(email => email.id !== id);
};

const shouldTruncate = (body) => body.length > 120;

const truncateBody = (body) => shouldTruncate(body) ? body.slice(0, 120) : body;

const expandBody = (email) => {
  email.expanded = !email.expanded;
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

const changePage = (direction) => {
  if (direction === 'prev' && currentPage.value > 1) {
    currentPage.value -= 1;
  } else if (direction === 'next' && currentPage.value < totalPages.value) {
    currentPage.value += 1;
  }
};
</script>


<style scoped>
  .inbox-title {
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
  
  .sender {
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
.pagination {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .pagination button {
    margin: 0 5px;
    padding: 8px;
    border: 1px solid #ddd;
    background-color: #fff;
    cursor: pointer;
  }

  .pagination button:disabled {
    cursor: not-allowed;
    background-color: #eee;
  }
  </style>
  
