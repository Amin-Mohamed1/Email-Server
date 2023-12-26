<template>
  <div>
    <h2 class="inbox-title">Sent</h2>
    <div class="search-bars-container">
      <div class="sort-bar">
        <select v-model="sortCategory" class="sort-category">
          <option value="datetime">Date</option>
          <option value="priority">Priority</option>
          <option value="subject">Subject</option>
        </select>
        <div class="sort-icons">
          <i class="fas fa-sort-alpha-down sort-icon" :class="{ 'rotate-icon': sortOrder === 'asc' }"
            @click="sortEmails('asc')"></i>
          <i class="fas fa-sort-alpha-up sort-icon" :class="{ 'rotate-icon': sortOrder === 'desc' }"
            @click="sortEmails('desc')"></i>
        </div>
      </div>
      <button @click="refreshPage" class="refresh-btn"><i class="fas fa-refresh animated-icon"></i></button>
      <div class="filter-bar">
        <select v-model="filterCategory" class="filter-category">
          <option value="datetime">Date</option>
          <option value="subject">Subject</option>
          <option value="reciever">Reciever</option>
        </select>
        <input v-model="filterQuery" type="text" placeholder="Filter Query...">
        <button @click="filter" class="filter-btn">
          <i class="fas fa-filter" @click="filterEmails"></i>
        </button>
      </div>
    </div>

    <div>
      <button @click="deleteSelectedEmails" :disabled="selectedEmailIds.length === 0" class="action-btn delete-btn">
        <i class="fas fa-delete"></i> delete
      </button>

      <div class="move-to-folder-container">

        <!--
      <button @click="moveToFolder" :disabled="selectedEmailIds.length === 0" class="action-btn move-btn">
        <i class="fas fa-folder"></i> Move to Folder
      </button>
      -->
        <!--
      <div v-if="selectedEmailIds.length > 0" class="folder-input">
        <input type="text" id="folderName" v-model="folderName" placeholder="Type folder name here"/>
      </div>
      -->
      </div>
    </div>

    <transition-group name="fade" mode="out-in">
      <div v-for="email in displayedEmails" :key="email.id" :class="{ 'email-item': true, 'unread-email': !email.read }">
        <div class="info">
          <div class="sender" @click="showEmailDetails(email)">{{ email.recievers.join(", ") }}</div>
          <div class="date-time" @click="showEmailDetails(email)">{{ email.dateTime }}</div>
        </div>
        <div class="subject" @click="showEmailDetails(email)">{{ email.subject }}</div>
        <div class="body" @click="showEmailDetails(email)">
          <div v-if="!email.expanded" class="truncated-body">
            {{ truncateBody(email.body, 120) }}
            <span v-if="shouldTruncate(email.body)" @click="showEmailDetails(email)">See more</span>
          </div>
          <div v-else>
            {{ email.body }}
            <span @click="showEmailDetails(email)">See less</span>
          </div>
          <div v-if="hasAttachment(email.attachments)" class="attachment-section" @click="showEmailDetails(email)">
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
          <input type="checkbox" v-model="selectedEmailIds" :value="email.id" class="select-btn"
            @change="handleCheckboxChange" style="position:relative; margin-left: 950px; width:30px; height:30px;" />
        </div>
      </div>
    </transition-group>

    <div v-if="show" class="modal">
      <div class="modal-content">
        <div class="info">
          <div class="sender">{{ selectedEmail.recievers.join(", ") }}</div>
          <div class="date-time">{{ selectedEmail.dateTime }}</div>
        </div>
        <div class="subject">{{ selectedEmail.subject }}</div>
        <div class="body">
          {{ selectedEmail.body }}
          <div v-if="hasAttachment(selectedEmail.attachments)" class="attachment-section">
            <strong class="attachment-label">Attachments:</strong>
            <ul>
              <li v-for="(attachment, index) in selectedEmail.attachments" :key="index">
                {{ getAttachmentIcon(attachment) }} <strong>{{ attachment }}</strong>
              </li>
            </ul>
          </div>
          <div v-else class="no-attachments">No attachments</div>
        </div>
        <div class="meta">
          <div class="priority"></div>
          <button @click.stop="closeEmailDetails" class="close-btn">
            <i class="fas fa-times"></i>
          </button>
        </div>
      </div>
    </div>
    <div class="pagination">
      <button @click="changePage('prev')" :disabled="currentPage === 1">Prev</button>
      <span>{{ currentPage }}</span>
      <button @click="changePage('next')" :disabled="currentPage === totalPages">Next</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { defineProps } from 'vue';

let selectedEmailIds = ref([]);

const deleteSelectedEmails = async () => {
  console.log('Selected Email IDs:', selectedEmailIds.value);
  var array1 = JSON.stringify(selectedEmailIds.value);
  try {
    const EmailAddress = props.profileContactInfo;
    const response = await fetch(`http://localhost:8081/mail/delete/${EmailAddress}/${"sent"}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
      body: array1
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    emails.value = data;
    console.log(data);
  } catch (error) {
    console.error('Error:', error);
  }
}


const filterQuery = ref('');
let defaultSortCategory = 'priority';
let sortCategory = ref(defaultSortCategory);
let defaultSortOrder = 'desc';
let sortOrder = ref(defaultSortOrder);
let filterCategory = 'reciever';
const emailsPerPage = 5;
let selectedEmail = null;
const props = defineProps(['profileContactInfo', 'Sentemails']);

const emails = ref([]);

props.Sentemails.forEach((inboxEmail) => {
  const transformedEmail = {
    id: inboxEmail.id,
    sender: inboxEmail.sender,
    subject: inboxEmail.subject,
    body: inboxEmail.body,
    attachments: inboxEmail.attatchments || [],
    priority: inboxEmail.priority,
    dateTime: inboxEmail.dateTime,
    read: inboxEmail.read,
    recievers: inboxEmail.recievers,
  };
  emails.value.push(transformedEmail);
});

const refreshPage = () => {
  fetchEmails();
};

const hasAttachment = (attachments) => attachments && attachments.length > 0;
let show = ref(false);

const showEmailDetails = (email) => {
  console.log('showEmailDetails called');
  makeRead(email);
  show.value = true;
  selectedEmail = email;
  console.log(selectedEmail);
};

const makeRead = async (email) => {
  try {
    const EmailAddress = props.profileContactInfo;
    const response = await fetch(`http://localhost:8081/mail/makeRead/${EmailAddress}/${email.id}`, {
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
    console.error('Error:', error);
  }
};
const closeEmailDetails = () => {
  show.value = false;
  selectedEmail = null;
};
const getAttachmentIcon = (attachment) => {
  if (attachment.endsWith('.jpeg') || attachment.endsWith('.png') || attachment.endsWith('.jpg')) {
    return '📷';
  } else if (attachment.endsWith('.docx')) {
    return '📃';
  } else if (attachment.endsWith('.pdf')) {
    return '📃';
  } else {
    return '📎';
  }
};

const sortEmails = async (sortOrder) => {
  try {
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "sent";
    const orderSorting = sortOrder;
    const sortingCategory = sortCategory;
    const order = orderSorting === "asc" ? true : false;
    console.log(order);

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


let filtering = false;
const filterEmails = async () => {
  try {
    filtering = true;
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "sent";
    const filteringCategory = filterCategory;
    const filteringQuery = filterQuery;

    console.log(" 1 " + currentFolder);
    console.log(" 2" + EmailAddress);
    console.log(" 3 " + filteringCategory);
    console.log(" 4 " + filteringQuery);

    const response = await fetch(`http://localhost:8081/mail/filter/${EmailAddress}/${currentFolder}/${filteringCategory}/${filteringQuery.value}`, {
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
/*
const fetchAndUpdateEmails = async () => {
  if(filtering == false){
    await fetchEmails();
  }
};
*/
onMounted(() => {
  fetchEmails();
  //fetchAndUpdateEmails();
  //setInterval(fetchAndUpdateEmails, 2000);
});


const fetchEmails = async () => {
  try {
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "sent";
    console.log(EmailAddress + " , " + currentFolder);

    const response = await fetch(`http://localhost:8081/mail/getEmails/${EmailAddress}/${currentFolder}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
      body: JSON.stringify({}),
    });

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    emails.value = data;
    console.log("fetch ", data);
  } catch (error) {
    console.error('Error fetching emails:', error);
  }
};

const currentPage = ref(1);
const totalPages = computed(() => Math.ceil(emails.value.length / emailsPerPage));
const displayedEmails = computed(() => {
  const startIndex = (currentPage.value - 1) * emailsPerPage;
  const endIndex = startIndex + emailsPerPage;
  return emails.value.slice(startIndex, endIndex);
});
const deleteEmail = (id) => {
  console.log(id);
  emails.value = emails.value.filter(email => email.id !== id);
};

const shouldTruncate = (body) => body.length > 120;
const truncateBody = (body, maxLength) => {
  return body.length > maxLength ? body.slice(0, maxLength) + '...' : body;
};
//const truncateBody = (body) => shouldTruncate(body) ? body.slice(0, 120) : body;

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
.action-btn {
  padding: 10px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  outline: none;
  font-size: 14px;
  margin-right: 10px;
}

.delete-btn {
  background-color: #dc3545;
  color: #fff;
  margin-bottom: 20px;
}

.delete-btn:hover {
  background-color: #c82333;
}

.move-btn {
  background-color: #007bff;
  color: #fff;
  margin-bottom: 20px;
}

.move-btn:hover {
  background-color: #0056b3;
}

.move-to-folder-container {
  display: flex;
  align-items: center;
}

.folder-input {
  display: flex;
  align-items: center;
  margin-left: 20px;

}

.folder-input label {
  margin-right: 10px;
  font-size: 14px;
  margin-bottom: 20px;
}

.folder-input input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-right: 10px;
  font-size: 14px;
  margin-bottom: 20px;
}

.folder-input button {
  padding: 10px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  outline: none;
  font-size: 14px;
  margin-right: auto;
}

.folder-input button:hover {
  background-color: #0056b3;
}

.folder-input input[type="text"]:focus {
  border-color: #007bff;
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}

.folder-input button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.email-item .checkbox-container {
  display: inline-block;
  margin-right: 10px;
}

.email-item .checkbox-container input[type="checkbox"] {
  display: none;
}

.email-item .custom-checkbox {
  width: 20px;
  height: 20px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  position: relative;
}

.email-item .custom-checkbox.checked {
  background-color: #007bff;
  border-color: #007bff;
}

.email-item .select-label {
  font-weight: bold;
  color: #0e8a24;
}

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

.refresh-btn {
  width: 40px;
  height: 40px;
  margin-top: 30px;
  background-color: #007BFF;
  color: #fff;
  border: none;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
  outline: none;
}

.refresh-btn:hover {
  background-color: #0056b3;
}

.animated-icon {
  font-size: 18px;
  animation: rotate 1s infinite linear;
}

@keyframes rotate {
  from {
    transform: rotate(0deg);
  }

  to {
    transform: rotate(360deg);
  }
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
  background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>');
  /* Add custom arrow icon */
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

.sort-bar {
  padding: 8px;
  border: 0px solid #ffffff;
  border-radius: 4px;
  margin-left: 2px;
  margin-right: 2px;
  color: #000000;
  background-color: #ffffff;
  outline: none;
  width: 30%;
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
  margin-left: 0px;
  margin-right: 2px;
  width: 600px;
  border: 1px solid #ffffff;
  border-radius: 4px;
  overflow: hidden;
  width: 100%;
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
  width: 100px;
  -webkit-appearance: none;
  background-image: url('data:image/svg+xml;utf8,<svg fill="%23333" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/><path d="M0 0h24v24H0z" fill="none"/></svg>');
  /* Add custom arrow icon */
  background-repeat: no-repeat;
  background-position: right 10px center;
}


.filter-category:focus {
  outline: none;
  border-color: #ffffff;
}


.filter-bar,
.filter-bar input {
  transition: border-color 0.3s;
}

.filter-bar {
  padding: 8px;
  border: 0px solid #ffffff;
  border-radius: 4px;
  margin-right: 10px;
  width: 600px;
  color: #000000;
  background-color: #ffffff;
  outline: none;
}

.filter-bar input {
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

.filter-bar .filter-btn i {
  font-size: 18px;
}

.filter-bar .filter-input::placeholder {
  color: #007BFF;
}

.filter-bar,
.filter-bar input:focus {
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

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 5px;
  max-width: 600px;
  width: 100%;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.close-btn {
  margin-left: 10px;
  cursor: pointer;
  padding: 8px 12px;
  border: 1px solid #e74c3c;
  background-color: #fff;
  color: #e74c3c;
  border-radius: 3px;
  transition: background-color 0.3s, color 0.3s;
}

.close-btn:hover {
  background-color: #e74c3c;
  color: #fff;
}


.modal-content,
.close-btn {
  transition: background-color 0.3s, color 0.3s;
}

.unread-email {
  font-weight: bold;
  background-color: #e2e2e2;
  border-left: 4px solid #170cab;
}
</style>
  