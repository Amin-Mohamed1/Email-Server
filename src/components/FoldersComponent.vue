<template>
  <div class="folders-container">
     <h2><i class="fa-solid fa-folder-plus"></i> Create New Folder</h2>
  
      <transition-group name="fade" mode="out-in">
        <div class="folderdiv">
       
            <input v-model="newFolderName" id="folderName" type="text" placeholder="Enter folder name" />
         </div>
          <div>
            <button class="newfolderbutton" @click="createFolder()">Create folder</button>
          </div>
        
        <h2 class="folders-title"><i class="fa fa-folder" style="margin-left: 10px;"></i> Folders</h2>
        <div v-for="folderName in folders" :key="folderName" class="foldersdiv">
           
          <label class="folderlabel">
            {{ folderName }}
            <a class="edit-icon" title="Rename folder" href="javascript:void(0)" @click="showRenameModal(folderName)">
              <i class="fa fa-pen" ></i>
            </a>
            <a class="delete-icon" title="Delete folder" href="javascript:void(0)" @click="deleteFolder(folderName)">
              <i class="fa fa-trash redtrash"></i>
            </a>
          </label>

          <table v-if="showFolders" class="table">
              <thead>
                <tr>
                  <th scope="col" class="selectcol" style="width: 50px"></th>
                  <th scope="col" class="prioritycol">Priority</th>
                  <th scope="col" class="sendercol">Sender</th>
                  <th scope="col" class="datecol">Date</th>
                  <th scope="col" class="subjectcol">Subject</th>
                  <th scope="col" class="contentcol">Content</th>
                  <th scope="col" class="downloadcol">Attachment</th>
                  <th scope="col" class="remove">Remove from folder</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="mail in hash_folder[folderName]" :key="mail.id">
        <td>
          <input type="checkbox" :checked="isSelected(mail.id)" @change="onchange(mail.id)" />
        </td>
                <!-- <tr v-for="mail in hash_folder[folderName]" :key="mail.id">
                  <td>
                    <input type="checkbox" :checked="selectedMails.includes(mail.id)" @change="onchange(mail.id)" />
 
                    <input type="checkbox" v-model="newFolderName" :value="mail.id" @change="onchange(mail.id)" />
                    <input type="checkbox" name="selected" @change="onchange(mail.id)"/> 
                  </td> -->
                  <td>{{ mail.priority }}</td>
                  <td>{{ mail.sender }}</td>
                  <td>{{ mail.date }}</td>
                  <td>{{ mail.subject }}</td>
                  <td>{{ mail.content }}</td>
                  <td>
                    <template v-if="mail.attachements.length !== 0">
                    <a href="javascript:void(0)" title="Download" @click="showAttachModal(mail)">
                      Download <i class="fa fa-download"></i>
                    </a>
                    </template>
                    <template v-else>
                      <label>No Attachments</label>
                    </template>
                  </td>

                  <td class="align-middle">
                    <a href="javascript:void(0)" @click="delMail(mail, '0', folderName)">
                <i class="fa fa-remove redtrash" style="font-size: 30px"></i>
                    </a>
                  </td>
                </tr>
              </tbody>
            </table>
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
      folders: ['Discrete', 'Numerical', 'Linear Algebra', 'Calculus'],
      hash_folder: [{
        'Discrete': [
          { id: 1, sender: 'John Doe', subject: 'Meeting Tomorrow', content: 'Lorem ipsum...', priority: 'High', date: '02/01/2023', attachments: [] },
        ],
        'Linear Algebra': [
          { id: 2, sender: 'Jane Smith', subject: 'Presentation Feedback', content: 'Your presentation was great!', priority: 'Medium', date: '03/01/2023', attachments: ['file1.pdf'] },
        ],
      },
      ],
    newFolderName: '',
      selectedMails: [],
    };
  },
  methods: {
    showRenameModal(folderName) {
      const newName = prompt(`Enter new name for folder "${folderName}":`);
      if (newName && newName.trim() !== '') {
        const index = this.folders.indexOf(folderName);
        if (index !== -1) {
          // Update folder name in folders array
          this.folders[index] = newName.trim();

          // Update key in hash_folder object
          this.hash_folder[newName.trim()] = [...this.hash_folder[folderName]];
          delete this.hash_folder[folderName];
        }
      }
    },
    deleteFolder(folderName) {
      if (confirm(`Are you sure you want to delete folder "${folderName}"?`)) {
        // Remove folder from the list
        this.folders.splice(this.folders.indexOf(folderName), 1);
        // Remove folder from hash_folder
        delete this.hash_folder[folderName];
      }
    },
     isSelected(mailId) {
      return this.selectedMails.includes(mailId);
    },
    onchange(mailId) {
      // Toggle the presence of mailId in the selectedMails array
      const index = this.selectedMails.indexOf(mailId);
      if (index === -1) {
        this.selectedMails.push(mailId);
      } else {
        this.selectedMails.splice(index, 1);
      }

      // Placeholder logic for handling checkbox change event
      console.log(`Selected mails:`, this.selectedMails);
    },
    showAttachModal(mail) {
      // Placeholder logic for showing attachment modal
      alert(`Attachment modal for mail with subject "${mail.subject}"`);
    },
    delMail(mail, param1, folderName) {
      if (confirm(`Are you sure you want to remove the mail with subject "${mail.subject}" from folder "${folderName}"?`)) {
        // Find index of mail in the folder
        const index = this.hash_folder[folderName].indexOf(mail);
        if (index !== -1) {
          // Remove mail from the folder
          this.hash_folder[folderName].splice(index, 1);
        }
      }
    },
    
    createFolder() {
      const trimmedFolderName = this.newFolderName.trim();
      if (trimmedFolderName !== '') {
        this.folders.push(trimmedFolderName);
        this.hash_folder[trimmedFolderName] = [];
        this.newFolderName = '';
      }
    }
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

.foldersdiv {
 width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}


.folderlabel {
  font-size: 20px;
  color: rgb(3, 16, 16);
  margin-right: 10px;
  font-weight: bold;
}

.edit-icon {
  color: #4caf50;
}
.edit-icon:hover {
  color: #45a049;
}
.delete-icon {
  color: #ff5555;
  margin-left: 5px;
}
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