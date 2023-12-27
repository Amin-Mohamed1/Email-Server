<template>
  <div class="folders-container">
    <h2><i class="fa-solid fa-folder-plus"></i> Create New Folder</h2>

    <transition-group name="fade" mode="out-in">
      <div class="folderdiv">
        <input v-model="newFolderName" id="folderName" type="text" placeholder="Enter folder name" />
      </div>
      <div>
        <button class="newfolderbutton" @click="createFolder">Create folder</button>
        <button @click="refreshPage" class="refresh-btn" style="margin-left: 10px;"><i
            class="fas fa-refresh animated-icon"></i></button>
      </div>

      <h2 class="folders-title"><i class="fa fa-folder" style="margin-left: 10px;"></i> Folders
      </h2>


      <div v-if="showFolders">
        <div v-for="(folder, index) in foldersData" :key="index" class="folderlabel">
          <h3>
            {{ folder.name }}
            <a class="edit-icon" title="Rename folder" href="javascript:void(0)" @click="showRenameModal(folder.name)">
              <i class="fa fa-pen"></i>
            </a>
            <a class="delete-icon" title="Delete folder" href="javascript:void(0)" @click="deleteFolder(folder.name)">
              <i class="fa fa-trash redtrash"></i>
            </a>
          </h3>
          <table class="table">
            <thead>
              <tr>
                <!-- <th scope="col" class="selectcol" style="width: 50px">No.</th> -->
                <th scope="col" class="prioritycol">Priority</th>
                <th scope="col" class="sendercol">Sender</th>
                <th scope="col" class="datecol">Date</th>
                <th scope="col" class="subjectcol">Subject</th>
                <th scope="col" class="contentcol">Content</th>
                <th scope="col" class="downloadcol">Attachment</th>
                <th scope="col" class="remove">Remove</th>
              </tr>
            </thead>
            <tbody>

              <tr v-for="(email, emailIndex) in folder.emails" :key="emailIndex"
                :class="{ 'email-item': true, 'unread-email': !email.read }" class="hoverable-row">

                <td>{{ email.priority }}</td>
                <td>{{ email.sender }}</td>
                <td>{{ email.date }}</td>
                <td>{{ email.subject }}</td>
                <td>{{ email.content }}</td>
                <td>
                </td>
                <td class="align-middle">
                  <a href="javascript:void(0)" @click="delMail(email, folder.name)">
                    <i id="delete" class="fa fa-remove redtrash" style="font-size: 30px; margin:auto;"></i>
                  </a>
                </td>
              </tr>

            </tbody>
          </table>
        </div>
      </div>
    </transition-group>





  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import { defineProps } from 'vue';
import '@fortawesome/fontawesome-free/css/all.css';

let selectedEmailIds = ref([]);
let selectedEmail = null;
const filterQuery = ref('');
let defaultSortCategory = 'dateTime';
let sortCategory = ref(defaultSortCategory);
let defaultSortOrder = 'desc';
let sortOrder = ref(defaultSortOrder);
let filterCategory = 'sender';
const emailsPerPage = 5;



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

const getParsedattatchments = (attatchments) => {
  const parsed = parseattatchments(attatchments);
  console.log('Parsed attatchments:', parsed);
  return parsed;
};

const showEmailDetails = (email) => {
  console.log('showEmailDetails called');
  makeRead(email);
  show.value = true;
  selectedEmail = email;
  console.log(selectedEmail);
};

const getattatchmentsIcon = (attatchment) => {
  if (attatchment.name.endsWith('.jpeg') || attatchment.name.endsWith('.png') || attatchment.name.endsWith('.jpg')) {
    return '📷';
  } else if (attatchment.name.endsWith('.docx')) {
    return '📃';
  } else if (attatchment.name.endsWith('.pdf')) {
    return '📃';
  } else {
    return '📎';
  }
};

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

const downloadAttachment = (attachment) => {
  const dataURI = `data:${attachment.type};base64,${attachment.format}`;
  const fileName = attachment.name;

  const downloadLink = document.createElement('a');
  downloadLink.href = dataURI;
  downloadLink.setAttribute('download', fileName);
  document.body.appendChild(downloadLink);
  downloadLink.click();

  document.body.removeChild(downloadLink);
};
export default {
  props: ['profileContactInfo'],
  data() {
    return {
      showFolders: true,
      folders: [],
      hash_folder: [{}],
      foldersData: [],
      newFolderName: '',
      selectedMails: [],
      profileAccount: 'profileContactInfo',
    };
  },
  mounted() {
    this.profileAccount = this.profileContactInfo;
    // this.fetchEmails();
    this.fetchFolders();
  },

  methods: {
    mouseOnEmail(id) {
      console.log(id)
      document.getElementById(id).style.backgroundColor = "#f0f0f0"
      console.log("haiaiai")
    },
    mouseNotOnEmail(id) {
      document.getElementById(id).style.backgroundColor = "#ffffff"
    },
    async showRenameModal(folderName) {
      const account = this.profileContactInfo;
      const newName = prompt('Enter new name for folder:');
      if (newName !== null && newName.trim() !== '') {
        const index = this.folders.indexOf(folderName);
        if (index !== -1) {
          const oldFolderName = this.folders[index];
          this.folders[index] = newName.trim();
          this.hash_folder[0][newName.trim()] = this.hash_folder[0][oldFolderName];
          delete this.hash_folder[0][oldFolderName];
        }
      }
      try {
        const response = await fetch(`http://localhost:8081/mail/renameFolder/${account}/${folderName}/${newName}`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        console.log('Folder renamed successfully on the backend:', data);
        this.fetchFolders();
      } catch (error) {
        console.error('Error renaming folder on backend:', error);
      }
    },
    async deleteFolder(folderName) {
      if (confirm('Are you sure you want to delete folder?')) {
        const account = this.profileContactInfo;

        try {
          const response = await fetch(`http://localhost:8081/mail/deleteFolder/${account}/${folderName}`, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
            },
          });

          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }

          const data = await response.json();
          console.log('Folder deleted successfully on the backend:', data);

          const index = this.folders.indexOf(folderName);
          if (index !== -1) {
            this.folders.splice(index, 1);
            delete this.hash_folder[0][folderName];
          }

          this.fetchFolders();
        } catch (error) {
          console.error('Error deleting folder on backend:', error);
        }

      }
    },
    isSelected(mailId) {
      return this.selectedMails.includes(mailId);
    },
    onchange(mailId) {
      const index = this.selectedMails.indexOf(mailId);
      if (index === -1) {
        this.selectedMails.push(mailId);
      } else {
        this.selectedMails.splice(index, 1);
      }
      console.log('Selected mails:', this.selectedMails);
    },
    showAttachModal(email) {
      if (email.attachments.length !== 0) {
        const attachmentFileName = email.attachments[0];
        const downloadLink = document.createElement('a');
        downloadLink.href = '/path/to / attachments / ${ attachmentFileName }';
        downloadLink.download = attachmentFileName;
        downloadLink.target = '_blank';
        downloadLink.click();
      } else {
        alert('No attachments to download.');
      }
    },
    async delMail(email, folderName) {
      if (confirm('Are you sure you want to remove this email from the folder ?')) {
        const account = this.profileContactInfo;
        const emailIds = [email.id];

        try {
          const response = await fetch(`http://localhost:8081/mail/delete/${account}/${folderName}`, {
            method: 'DELETE',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(emailIds),
          });

          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
          }

          const data = await response.text();
          console.log(JSON.parse(data))
          console.log('Email deleted successfully on the backend:', data);
          this.fetchFolders();
        } catch (error) {
          console.error('Error deleting email on backend:', error);
        }
      }
    },
    createFolder() {
      const trimmedFolderName = this.newFolderName.trim();
      if (trimmedFolderName !== '' && this.profileContactInfo) {
        try {
          this.folders.push(trimmedFolderName);
          this.hash_folder[0][trimmedFolderName] = [];
          console.log('Creating folder:', trimmedFolderName);
          console.log('Creating account:', this.profileContactInfo);
          this.sendFolderToBackend(trimmedFolderName, this.profileContactInfo);
          this.newFolderName = '';
        } catch (error) {
          console.error('Error creating folder:', error.message);
        }
      }
    },
    async sendFolderToBackend(folderName, accountName) {
      try {
        const account = accountName;
        const folder = folderName;
        console.log('Sending folder to backend:', folder);
        console.log('Sending account to backend:', account);
        const response = await fetch(`http://localhost:8081/mail/addFolder/${account}/${folder}`);
        console.log(response.status);
        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.text();
        this.foldersData = JSON.parse(data)
        console.log(this.foldersData)
        console.log('Folder created successfully on the backend:', data);
      } catch (error) {
        console.error('Error sending folder to backend:', error);
      }
    },

    refreshPage() {
      //  this.fetchEmails();
      this.fetchFolders();
    },

    // async fetchEmails() {
    //   try {
    //     const EmailAddress = this.profileContactInfo; // assuming profileContactInfo is a data property
    //     const currentFolder = "inbox";
    //     console.log(EmailAddress + " , " + currentFolder);

    //     const response = await fetch(`http://localhost:8081/mail/getEmails/${EmailAddress}/${currentFolder}`, {
    //       method: 'POST',
    //       headers: {
    //         'Content-Type': 'application/json',
    //       },
    //       mode: 'cors',
    //       body: JSON.stringify({}),
    //     });

    //     if (!response.ok) {
    //       throw new Error(`HTTP error! Status: ${response.status}`);
    //     }

    //     const data = await response.json();
    //     this.emails = data; // assuming emails is a data property
    //     console.log("fetch ", data);
    //   } catch (error) {
    //     console.error('Error fetching emails:', error);
    //   }
    // },

    async fetchFolders() {
      try {
        const EmailAddress = this.profileContactInfo; // assuming profileContactInfo is a data property

        console.log(EmailAddress);
        const response = await fetch(`http://localhost:8081/mail/showFolder/${EmailAddress}`);

        if (!response.ok) {
          throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.text();
        // this.emails = data; // assuming emails is a data property
        this.foldersData = JSON.parse(data)
      } catch (error) {
        console.error('Error fetching emails:', error);
      }
    },
  }

};
</script>

<style scoped>
h2 {
  color: #187dc1;
  font-size: 28px;
  margin-bottom: 15px;
}

.folders-container {
  max-width: 1000px;
  margin: auto;
  padding: 20px;
}

.folderdiv {
  max-width: 400px;
  margin: auto;
  margin-bottom: 20px;
}

input {
  height: 100%;
  width: 100%;
  outline: none;
  padding: 10px;
  font-size: 16px;
  color: #333;
  border: 1.5px solid #C7BEBE;
  border-bottom-width: 2.5px;
  border-radius: 6px;
  transition: all 0.3s ease;
}

input:focus,
input:hover {
  border-color: #4070f4;
}

.newfolderbutton {
  background: #4070f4;
  margin-left: 450px;
  color: #fff;
  letter-spacing: 1px;
  border: none;
  cursor: pointer;
  padding: 10px;
  border-radius: 4px;
  transition: background-color 0.3s;

}



.newfolderbutton:hover {
  background: #0e4bf1;
}

.table:hover {
  /* background-color: #f0f0f0; */
}


.folderlabel {
  font-size: 20px;
  color: rgb(3, 16, 16);
  margin-right: 10px;
  font-weight: bold;
}

#download,
.edit-icon {
  color: #4caf50;
}

#download:hover,
.edit-icon:hover {
  color: #45a049;
}

#delete,
.delete-icon {
  color: #ff5555;
  margin-left: 5px;
}

#delete:hover,
.delete-icon:hover {
  color: #dd4444;
}

th,
td {
  border: 1px solid #ddd;
  padding: 12px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
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


.hoverable-row:hover {
  background-color: #f0f0f0;
}
</style>