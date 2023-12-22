<template>
    <div class="folders-container">
      <h2><i class="fa-solid fa-folder-plus"></i> Create New Folder</h2>

      <transition-group name="fade" mode="out-in">
        <div class="folderdiv">
          <input v-model="newFolderName" id="folderName" type="text" placeholder="Enter folder name" />
        </div>
        <div>
          <button class="newfolderbutton" @click="createFolder">Create folder</button>
        </div>

        <h2 class="folders-title"><i class="fa fa-folder" style="margin-left: 10px;"></i> Folders</h2>

        <div v-if="showFolders">
          <div v-for="(folder, folderName) in hash_folder[0]" :key="folderName" class="folderlabel">
            <h3>
              {{ folderName }}
              <a class="edit-icon" title="Rename folder" href="javascript:void(0)" @click="showRenameModal(folderName)">
                <i class="fa fa-pen"></i>
              </a>
              <a class="delete-icon" title="Delete folder" href="javascript:void(0)" @click="deleteFolder(folderName)">
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
              <tr v-for="email in folder" :key="email.id">
                <!-- <td>
                  {{ email.id  }}
                  <a href="javascript:void(0)" :checked="isSelected(email.id)" @change="onchange(email.id)" >
                  </a>
                </td> -->
                <td>{{ email.priority }}</td>
                <td>{{ email.sender }}</td>
                <td>{{ email.date }}</td>
                <td>{{ email.subject }}</td>
                <td>{{ email.content }}</td>
                <td>
                  <template v-if="email.attachments.length !== 0">
                    <a href="javascript:void(0)" id="download" title="Download" @click="showAttachModal(email)">
                      Download <i id="download"  class="fa fa-download"></i>
                    </a>
                  </template>
                  <template v-else>
                    <label>No Attachments</label>
                  </template>
                </td>
                <td class="align-middle">
                  <a href="javascript:void(0)" @click="delMail(email, '0', folderName)">
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
import { ref } from 'vue';

export default {
  data() {
    return {
      showFolders: true,
      folders: [],
      // folders: ['Discrete', 'Numerical', 'Linear Algebra', 'Calculus'],
      hash_folder: [],
      // hash_folder: [
      //   {
      //     'Discrete': [
      //       { id: 1, sender: 'John Doe', subject: 'Meeting Tomorrow', content: 'Lorem ipsum...', priority: 'High', date: '02/01/2023', attachments: [] },
      //     ],
      //     'Linear Algebra': [
      //       { id: 2, sender: 'Jane Smith', subject: 'Presentation Feedback', content: 'Your presentation was great!', priority: 'Medium', date: '03/01/2023', attachments: ['drafts.png'] },
      //     ],
      //   },
      // ],
      newFolderName: '',
      selectedMails: [],
    };
  },
  methods: {
   showRenameModal(folderName) {
      const newName = prompt(`Enter new name for folder "${folderName}":`);
      if (newName !== null && newName.trim() !== '') {
        const index = this.folders.indexOf(folderName);
        if (index !== -1) {
          const oldFolderName = this.folders[index];
          this.folders[index] = newName.trim();
          this.hash_folder[0][newName.trim()] = this.hash_folder[0][oldFolderName];
          delete this.hash_folder[0][oldFolderName];
        }
      }
    },
    deleteFolder(folderName) {
      if (confirm(`Are you sure you want to delete folder "${folderName}"?`)) {
        const index = this.folders.indexOf(folderName);
        if (index !== -1) {
          this.folders.splice(index, 1);
          delete this.hash_folder[0][folderName];
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
        downloadLink.href = `/path/to/attachments/${attachmentFileName}`;
        downloadLink.download = attachmentFileName;
        downloadLink.target = '_blank'; 
        downloadLink.click();
      } else {
        alert('No attachments to download.');
      }
    },
    delMail(email, param1, folderName) {
      if (confirm(`Are you sure you want to remove the email with subject "${email.subject}" from folder "${folderName}"?`)) {
        const index = this.hash_folder[0][folderName].indexOf(email);
        if (index !== -1) {
          this.hash_folder[0][folderName].splice(index, 1);
        }
      }
    },
     createFolder() {
      const trimmedFolderName = this.newFolderName.trim();
      if (trimmedFolderName !== '') {
        this.folders.push(trimmedFolderName);
        this.hash_folder[0][trimmedFolderName] = [];
        this.newFolderName = '';
      }
    },
  }
};
</script>

<style scoped>
h2{
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
  margin-left: 450px ;
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

  .table:hover{
   background-color: #f0f0f0;
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
</style>