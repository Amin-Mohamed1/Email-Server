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
      <button @click="refreshPage" class="refresh-btn"><i class="fas fa-refresh animated-icon"></i></button>
      <div class="filter-bar">
        <select v-model="filterCategory" class="filter-category">
          <option value="sender">Sender</option>
          <option value="dateTime">Date</option>
          <option value="subject">Subject</option>
        </select>
        <input v-model="filterQuery" type="text" placeholder="Filter Query...">
        <button @click="filter" class="filter-btn">
          <i class="fas fa-filter" @click="filterEmails"></i>
        </button>
      </div>
    </div>

  <div>
    <button @click="deleteSelectedEmails" :disabled="selectedEmailIds.length === 0" class="action-btn delete-btn">
      <i class="fas fa-trash"></i> Delete
    </button>

    <div class="move-to-folder-container">
      <button @click="moveToFolder" :disabled="selectedEmailIds.length === 0" class="action-btn move-btn">
        <i class="fas fa-folder"></i> Move to Folder
      </button>

      <div v-if="selectedEmailIds.length > 0" class="folder-input">
        <input type="text" id="folderName" v-model="folderName" placeholder="Type folder name here"/>
      </div>
    </div>
  </div>

    <transition-group name="fade" mode="out-in">
        <div
          v-for="email in displayedEmails"
          :key="email.id"
          :class="{ 'email-item': true, 'unread-email': !email.read }"
        >
      <span v-if="selectedEmailIds.includes(email.id)" class="select-label">Selected</span>

        <div class="info" @hover="showEmailDetails(email)">
          <div class="sender">{{ email.sender }}</div>
          <div class="date-time">{{ email.dateTime }}</div>
          <!--<div> {{ email.attatchments }}</div>-->
        </div>
        <div class="subject" @click="showEmailDetails(email)">{{ email.subject }}</div>
        <div class="body" @click="showEmailDetails(email)">
          <div v-if="!email.expanded" class="truncated-body">
              {{ truncateBody(email.body, 120) }}
              <span v-if="shouldTruncate(email.body)" @click="showEmailDetails(email)">See more</span>
          </div>
          <div v-else @click="showEmailDetails(email)">
            {{ email.body }}
            <span @click="showEmailDetails(email)">See less</span>
          </div>
          <!-- 
            <div v-if="hasattatchments(email.attatchments)" class="attatchments-section" @click="showEmailDetails(email)">
              <strong class="attatchments-label">attatchments:</strong>
              <ul>
                <li v-for="(attatchments, index) in email.attatchments" :key="index">
                  {{ getattatchmentsIcon(attatchments) }} <strong>{{ attatchments.name }}</strong>
                </li>
              </ul>
            </div>

            <div v-else class="no-attatchments" @click="showEmailDetails(email)">No attatchments</div>
          -->
          <div v-if="true" class="attatchments-section" @click="showEmailDetails(email)">
              <strong class="attatchments-label">attatchments:</strong>
              <ul>
                <li v-for="(attachment, index) in email.attatchments" :key="index">
                  {{ getattatchmentsIcon(attachment) }}<strong>{{ attachment.name }}</strong>
                </li>
              </ul>
            </div>
          </div>
        <div class="meta">
          <div class="priority">Priority: {{ email.priority }}</div>
          <div class="rating">
              <i
                v-for="index in 5"
                :key="index"
                class="fas fa-star"
                :class="{ 'glow': index <= email.priority }"
                @click="updatePriority(email.id, index)"
              ></i>
            </div>
          <!-- -<input type="range" v-model="email.priority" min="1" max="5" @change="updatePriority(email.id, email.priority)">
          -->
            <input
            type="checkbox"
            v-model="selectedEmailIds"
            :value="email.id"
            class="select-btn"
            @change="handleCheckboxChange"
            style = "position:relative; margin-left: 100px; width:30px; height:30px;"
            />
        </div>
      </div>
    </transition-group>
    <div v-if="show" class="modal" >
      <div class="modal-content">
        <div class="info">
          <div class="sender">{{ selectedEmail.sender }}</div>
          <div class="date-time">{{ selectedEmail.dateTime }}</div>
        </div>
        <div class="subject">{{ selectedEmail.subject }}</div>
        <div class="body">
            {{ selectedEmail.body }}
            <div>
                  <strong class="attatchments-label">attatchments:</strong>
                  <ul>
                    <li v-for="(attachment, index) in selectedEmail.attatchments" :key="index">
                      {{ getattatchmentsIcon(attachment) }}<strong>{{ attachment.name }}</strong>
                    </li>
                  </ul>
            </div>
        </div>
        <div class="meta">
          <div class="priority">Priority: {{ selectedEmail.priority }}</div>
          <button @click.stop="closeEmailDetails" class="close-btn">
            <i class="fas fa-times"></i>
          </button>
        </div>
      </div>
    </div>
  </div>

  <div class="pagination">
          <button @click="changePage('prev')" :disabled="currentPage === 1">
            <i class="fas fa-chevron-left"></i>
          </button>
          <span>{{ currentPage }}</span>
          <button @click="changePage('next')" :disabled="currentPage === totalPages">
            <i class="fas fa-chevron-right"></i>
          </button>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { defineProps } from 'vue';
import '@fortawesome/fontawesome-free/css/all.css';


const filterQuery = ref('');
let defaultSortCategory = 'dateTime';
let sortCategory = ref(defaultSortCategory);
let defaultSortOrder = 'desc';
let sortOrder = ref(defaultSortOrder);
let filterCategory = 'sender';
const emailsPerPage = 5;
let selectedEmail= null;
const props = defineProps(['profileContactInfo', 'Inboxemails']);
const emails = ref([]);
let selectedEmailIds = ref([]); 

const updatePriority = async (emailId, priority) => {
  try {
    const EmailAddress = props.profileContactInfo;
    const response = await fetch(
      `http://localhost:8081/mail/editPriority/${EmailAddress}/${emailId}/${priority}`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    emails.value = data;
    console.log(data);
  } catch (error) {
    console.error('Error updating priority:', error);
  }

};
const moveToFolder = async() =>{
  console.log('Selected Email IDs:', selectedEmailIds.value);
  var array1 = JSON.stringify(selectedEmailIds.value);
  var folderDestinaton = folderName.value;
  console.log(folderDestinaton);
  try {
    const EmailAddress = props.profileContactInfo;
    const response = await fetch(`http://localhost:8081/mail/moveEmails/${EmailAddress}/${folderDestinaton}`, {
      method: 'POST',
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

const deleteSelectedEmails = async() =>{
  console.log('Selected Email IDs:', selectedEmailIds.value);
  var array1 = JSON.stringify(selectedEmailIds.value);
  try {
    const EmailAddress = props.profileContactInfo;
    const response = await fetch(`http://localhost:8081/mail/delete/${EmailAddress}/${"inbox"}`, {
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

props.Inboxemails.forEach((inboxEmail) => {
  const transformedEmail = {
    id: inboxEmail.id,
    sender: inboxEmail.sender,
    subject: inboxEmail.subject,
    body: inboxEmail.body,
    attatchments: inboxEmail.attatchments || [],
    priority: inboxEmail.priority,
    dateTime: inboxEmail.dateTime,
    read:inboxEmail.read,
  };
  emails.value.push(transformedEmail);
});


const refreshPage = () => {
  fetchEmails(); 
};


const getattatchmentsIcon = (attachment) => {
  if (attachment.name.endsWith('.jpeg') || attachment.name.endsWith('.png')  || attachment.name.endsWith('.jpg')  ) {
    return '📷'; 
  } else if (attachment.name.endsWith('.docx')) {
    return '📃';
  } else if (attachment.name.endsWith('.pdf')) {
    return '📃';
  } else {
    return '📎';
  }
};

let show = ref(false);
const showEmailDetails = (email) => {
    console.log('showEmailDetails called');
    makeRead(email);
    show.value = true;
    selectedEmail = email;
    console.log(selectedEmail);
};
const deleteThis = (email) => {
    console.log("delete here!");
    console.log(email.id);
};


const makeRead = async (email) => {
  try {
    const EmailAddress = props.profileContactInfo;
    const response = await fetch(`http://localhost:8081/mail/makeRead/${EmailAddress}/${"inbox"}/${email.id}`, {
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

  sortCategory = "datetime";
  sortEmails("desc");
};
const closeEmailDetails = () => {
    show.value = false;
    selectedEmail = null;
};

/*
const getattatchmentsIcon = (attatchments) => {
  if (attatchments.endsWith('.jpeg') || attatchments.endsWith('.png')  || attatchments.endsWith('.jpg')  ) {
    return '📷'; 
  } else if (attatchments.endsWith('.docx')) {
    return '📃';
  } else if (attatchments.endsWith('.pdf')) {
    return '📃';
  } else {
    return '📎';
  }
};
*/
const sortEmails = async (sortOrder) => {
  try {
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "inbox";
    const orderSorting = sortOrder;
    const sortingCategory = sortCategory;
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

let filtering = false;
const filterEmails = async () => {
  try {
    filtering = true;
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "inbox";
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

onMounted(() => {
  fetchEmails();
});

/*
const fetchEmails = async () => {
  try {
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "inbox";
    console.log(EmailAddress +  " , "  + currentFolder);

    const response = await fetch(`http://localhost:8081/mail/getEmails/${EmailAddress}/${currentFolder}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      mode: 'cors',
      body: JSON.stringify({  }),
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
  sortEmails("desc");
};
*/
let parsedEmails = [];
const fetchEmails = async () => {
  try {
    const EmailAddress = props.profileContactInfo;
    const currentFolder = "inbox";

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

    const responecedata = await response.json();
    console.log('Data:', responecedata);
    parsedEmails = responecedata.map((inboxEmail) => {
      const transformedEmail = {
        id: inboxEmail.id,
        sender: inboxEmail.sender,
        subject: inboxEmail.subject,
        body: inboxEmail.body,
        attatchments: getParsedattatchments(inboxEmail.attatchments),
        priority: inboxEmail.priority,
        dateTime: inboxEmail.dateTime,
        read: inboxEmail.read,
      };
      return transformedEmail;
    });
    
    console.log(parsedEmails.value  + ' kkk');

    emails.value = parsedEmails;
    console.log("fetch ", parsedEmails);
  } catch (error) {
    console.error('Error fetching emails:', error);
  }
};

const parseattatchments = (attatchments) => {
  if (!Array.isArray(attatchments)) {
    attatchments = [attatchments];
  }

  console.log("hellooo");

  return attatchments.map((attatchments) => {
    let name = attatchments && attatchments.name ? attatchments.name : "N/A";
    let type = attatchments && attatchments.type ? attatchments.type : "N/A";
    let format = attatchments && attatchments.format ? attatchments.format : "N/A";

    return {
      name,
      type,
      format,
    };
  });
};

const getParsedattatchments = (attatchments) => {
  const parsed = parseattatchments(attatchments);
  console.log('Parsed attatchments:', parsed);
  return parsed;
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

.rating {
  display: inline-block;
}

.fa-star {
  font-size: 24px;
  color: #888888; /* Default star color */
  transition: color 0.3s ease; /* Transition effect for the color change */
}

.glow {
  color: rgb(240, 235, 203); /* Color for glowing stars */
  animation: glow 1s infinite alternate; /* CSS animation for glowing effect */
}

@keyframes glow {
  to {
    color: #ffd700;
  }
}

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
  
  .refresh-btn {
  width:40px;
  height:40px;  
  margin-top:30px;
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
  .attatchments-indicator {
  margin-left: 5px;
}

.attatchments-section {
  margin-top: 10px;
}

.attatchments-label {
  font-weight: bold;
}

.no-attatchments {
  color: #888;
  margin-top: 10px;
}

ul {
  list-style: none;
  padding: 0;
}.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.pagination button {
  margin: 0 5px;
  padding: 8px 12px;
  border: 1px solid #ddd;
  background-color: #fff;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s, color 0.3s, border-color 0.3s;
  font-size: 14px;
}

.pagination button:disabled {
  cursor: not-allowed;
  background-color: #eee;
  color: #888;
  border-color: #eee;
}

.pagination button:hover {
  background-color: #007BFF;
  color: #fff;
  border-color: #007BFF;
}

.pagination span {
  margin: 0 5px;
  padding: 8px 12px;
  border: 1px solid #ddd;
  background-color: #eee;
  border-radius: 4px;
  font-size: 14px;
  color: #888;
}

.pagination span.current {
  background-color: #007BFF;
  color: #fff;
  border-color: #007BFF;
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
    margin-left: 0px;
    margin-right: 2px;
    width: 600px;
    border: 1px solid #ffffff;
    border-radius: 4px;
    overflow: hidden;
    width:100%;
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
