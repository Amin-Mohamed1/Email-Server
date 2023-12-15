<template>
      <h2 class="folders-title">Folders<i class="fa fa-folder" style="margin-left: 10px;"></i></h2>
    
      <transition-group name="fade" mode="out-in">
        <div class="folderdiv">
          <div class="newfolderform">
            <label class="create" for="folderName">Create New Folder</label>
            <input v-model="newFolderName" id="folderName" type="text" placeholder="Enter folder name" />
          </div>
          <div>
            <button class="newfolderbutton" @click="createFolder()">Create new folder <i class="fa fa-plus" style="color: azure; font-size: 18px"></i></button>
          </div>
        </div>
        <div v-for="folderName in folders" :key="folderName" class="foldersdiv">
          <label class="folderlabel">
            {{ folderName }}
            <a class="icon" title="Rename folder" href="javascript:void(0)" @click="showRenameModal(folderName)">
              <i class="fa fa-pen" style="color: #0056b3; font-size: 20px; margin-left: 20px"></i>
            </a>
            <a class="icon" title="Delete folder" href="javascript:void(0)" @click="deleteFolder(folderName)">
              <i class="fa fa-trash redtrash" style="color: rgb(202, 59, 59); font-size: 20px; margin-left: 10px"></i>
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


<style>
.folders-title {
    font-size: 30px;
    margin-bottom: 20px;
    color: #333;
  }
  
  .folderdiv {
    display: flex;
    flex-direction: row;
    background-color: #4196f1;
    padding: 15px;
    margin-bottom: 10px;
    border-radius: 5px;
    border: #333 4px outset;
  }
  
  .folderdiv:hover{
   transform: scale(1.02);
}
  .create{
    color: azure;
    font-size: 30px;
    margin-right: 10px;
    font-weight: normal;
  }

  .newfolderform {
    display: flex;
    flex-direction: row;
    align-items: center;
  }

  .newfolderform:hover{
   transform: scale(1.02);
}

  input {
    width: 300px;
    height: 30px;
    border-radius: 5px;
    border: none;
    padding-left: 10px;
    font-size: 20px;
    margin-left: 15px;
  }

  .newfolderbutton {
    background-color: #0056b3;
    color: azure;
    font-size: 20px;
    border: none;
    border-radius: 5px;
    padding: 10px;
    margin-left: 25px;
    cursor: pointer;
  }

  .newfolderbutton:hover {
    background-color: #003d80;
  }

  .table:hover{
   transform: scale(1.02);
}

.foldersdiv {
  display: flex;
  flex-direction: row;
  background-color: #4196f1;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 5px;
}

.foldersdiv:hover{
   transform: scale(1.02);
}

.folderlabel {
  font-size: 20px;
  color: azure;
  margin-right: 10px;
  font-weight: bold;
}

i:hover{
  color: #0056b3;
  transform: scale(1.02);
}

.icon{
  display: inline-flex;
    flex-direction: row;
}

table, th, td {
  border: 1px solid black;
  text-align: center;
}

table {
  border-collapse: collapse;
  width: 100%;
  font-weight: bold;
  color: #0a0c0e;
  font-size: 16px;
  margin: 0px;
}
</style>