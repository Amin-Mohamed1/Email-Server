<template>
  <div>
    <h2 class="inbox-title">Inbox</h2>
    <div class="search-bars-container">
      <div class="sort-bar">
        <select v-model="sortCategory" class="sort-category">
          <option value="dateTime">Date</option>
          <option value="priority">Priority</option>
          <option value="sender">Sender</option>
          <option value="subject">Subject</option>
        </select>
        <div class="sort-icons">
        <i class="fas fa-sort-alpha-down sort-icon" :class="{ 'rotate-icon': sortOrder === 'asc' }" @click="sortEmails('asc')"></i>
        <i class="fas fa-sort-alpha-up sort-icon" :class="{ 'rotate-icon': sortOrder === 'desc' }" @click="sortEmails('desc')"></i>
        </div>
      </div>

      <div class="filter-bar">
        <select v-model="filterCategory" class="filter-category">
          <option value="reciever">Reciever</option>
          <option value="date">Date</option>
          <option value="subject">Subject</option>
        </select>
        <input v-model="filterQuery" type="text" placeholder="Filter Query...">
        <button @click="filter" class="filter-btn">
          <i class="fas fa-filter"></i>
        </button>
      </div>

    </div>

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
                  {{ getAttachmentIcon(attachment) }} <strong>{{ attachment }}</strong>
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
import { defineProps } from 'vue';

let searchCategory = 'sender';
let defaultSortCategory = 'priority';
let sortCategory = ref(defaultSortCategory);
let defaultSortOrder = 'desc';
let sortOrder = ref(defaultSortOrder);
let filterCategory = 'reciever';
const emailsPerPage = 5;

const props = defineProps(['profileContactInfo', 'Draftemails']);

const emails = ref([]);

props.Draftemails.forEach((draftEmail) => {
  const transformedEmail = {
    id: draftEmail.id,
    sender: draftEmail.sender,
    subject: draftEmail.subject,
    body: draftEmail.body,
    attachments: draftEmail.attatchments || [],
    priority: draftEmail.priority,
    dateTime: draftEmail.dateTime,
  };
  emails.value.push(transformedEmail);
});

const hasAttachment = (attachments) => attachments && attachments.length > 0;
/*
const getAttachmentIcon = (attachment) => {
  // Customize this based on your requirements
  return attachment.endsWith('.png') ? '🖼️' : attachment.endsWith('.jpeg') ? '📷' : '📎';
};
*/


const getAttachmentIcon = (attachment) => {
  if (attachment.endsWith('.jpeg') || attachment.endsWith('.png')  || attachment.endsWith('.jpg')  ) {
    return '📷'; 
  } else if (attachment.endsWith('.docx')) {
    return '📃';
  } else if (attachment.endsWith('.pdf')) {
    return '📃'; // Image icon
  } else {
    return '📎'; // Default attachment icon
  }
};


const fetchAndUpdateEmails = async () => {
  console.log("i");
  await fetchEmails();
};

onMounted(() => {
  fetchAndUpdateEmails();
  setInterval(fetchAndUpdateEmails, 2000);
});

const fetchEmails = async () => {
  try {
    const EmailAddress = ref(props.profileContactInfo);
    const response = await axios.get(`http://localhost/emails/${EmailAddress}`);
    emails.value = response.data;
  } catch (error) {
    console.error('Error fetching emails:', error);
  }
};

const sortEmails = async (sortOrder) => {
  try {
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "inbox";
    const orderSorting = sortOrder;
    const sortingCategory = sortCategory; // No need to use ref here

    // Use .value to access the values of refs
    const order = orderSorting === "asc" ? true : false;

    const response = await fetch(`http://localhost:8081/mail/sortEmail/${EmailAddress}/${currentFolder}/${sortingCategory.value}/${order}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    emails.value = data;

    console.log(data);
  } catch (error) {
    console.error('Error sorting emails:', error);
  }
};

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


const changePage = (direction) => {
  if (direction === 'prev' && currentPage.value > 1) {
    currentPage.value -= 1;
  } else if (direction === 'next' && currentPage.value < totalPages.value) {
    currentPage.value += 1;
  }
};

</script>


<style scoped>

.search-bars-container {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px; 
  }

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
    background-color: #ffffff;
  }
  .sort-bar {
    display: flex;
    margin-top: 20px;
    transition: transform 0.3s;
    margin-bottom: 20px;
    margin-left: 2px;
    margin-right: 2px;
    width: 600px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    overflow: hidden;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }

  .sort-bar:hover {
    transform: scale(1.05);
  }

  .sort-category {
    padding: 10px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    background-color: #ffffff;
    color: #000000;
    cursor: pointer;
    appearance: none;
    width: 100px;
    -webkit-appearance: none;
    background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>'); /* Add custom arrow icon */
    background-repeat: no-repeat;
    text-align: center;
    background-position: left 70px bottom 10px; 
  }

  .sort-category:focus {
    outline: none;
    border-color: #ffffff;
  }

  .sort-bar,
  .sort-bar input {
    transition: border-color 0.3s;
  }

  .sort-bar  {
    padding: 8px;
    border: 0px solid #ffffff;
    border-radius: 4px;
    margin-left: 2px;
    margin-right: 2px;
    color: #000000;
    background-color: #ffffff;
    outline: none;
    width:30%;
  }

  .sort-bar input {
    flex: 1;
    padding: 8px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    color: #000000;
    outline: none;
  }

  .sort-bar .sort-btn {
    background-color: #007BFF;
    color: #fff;
    border: none;
    padding: 8px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    outline: none;
  }

  .sort-bar .sort-btn i {
    font-size: 18px;
  }

  .sort-bar .sort-input::placeholder {
    color: #007BFF;
  }

  .sort-bar,
  .sort-bar input:focus {
    border-color: #053a72;
  }
  .filter-bar {
    display: flex;
    margin-top: 20px;
    transition: transform 0.3s;
    margin-bottom: 20px;
    margin-left: 10px;
    margin-right: 2px;
    width: 600px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    overflow: hidden;
    width:90%;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  }


   .filter-bar:hover {
    transform: scale(1.05);
  }

   .filter-category {
    padding: 10px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    background-color: #ffffff;
    color: #000000;
    cursor: pointer;
    appearance: none;
    width:100px;
    -webkit-appearance: none;
    background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>'); /* Add custom arrow icon */
    background-repeat: no-repeat;
    background-position: right 10px center;
  }


 .filter-category:focus {
    outline: none;
    border-color: #ffffff;
  }


  .filter-bar,
  .filter-bar input
  {
    transition: border-color 0.3s;
  }

.filter-bar  {
    padding: 8px;
    border: 0px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    width:600px;
    color: #000000;
    background-color: #ffffff;
    outline: none;
  }

.filter-bar input  {
    flex: 1;
    padding: 8px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    margin-right: 10px;
    color: #000000;
    outline: none;
  }
.filter-bar .filter-btn {
    background-color: #007BFF;
    color: #fff;
    border: none;
    padding: 8px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
    outline: none;
  }

 .filter-bar .filter-btn i  {
    font-size: 18px;
  }

 .filter-bar .filter-input::placeholder {
    color: #007BFF;
  }

  .filter-bar,
  .filter-bar input:focus{
    border-color: #053a72;
  }
.sort-icons {
  display: flex;
  align-items: center;
}

.sort-icon {
  margin-top: 10px;
  margin-right: 0px; 
  margin-left: 70px; 
}

.rotate-icon {
  transform: rotate(180deg);
}
</style>
  
