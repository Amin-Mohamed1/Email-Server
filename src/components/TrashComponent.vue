<template>
  <div class="trash-container">
  <h2 class="trash-title"><i class="fa fa-trash redtrash" style="margin-left: 10px;"></i> Trash</h2>
 
   <transition-group name="fade" mode="out-in">
     <table v-if="showTrash" class="trash-list ">
     <thead>
       <tr>
         <th scope="col" class="selectcol"></th>
         <th scope="col" class="prioritycol">Priority</th>
         <th scope="col" class="sendercol">Sender</th>
         <th scope="col" class="datecol">Date</th>
         <th scope="col" class="subjectcol">Subject</th>
         <th scope="col" class="contentcol">Content</th>
         <th scope="col" class="trashcol">Delete Forever</th>
       </tr>
     </thead>
     <tbody>
       <tr v-for="mail in mails" :key="mail.id" class="hoverable-row">
         <td>
           <input type="checkbox" v-model="selectedMails" :value="mail.id" @change="onchange" />
         </td>
         <td>{{ mail.priority }}</td>
         <td>{{ mail.sender }}</td>
         <td>{{ mail.date }}</td>
         <td>{{ mail.subject }}</td>
         <td>{{ mail.content }}</td>
         <td class="align-middle">
           <a href="javascript:void(0)" @click="totalyRemove(mail)">
             <i id = "icon" class="fa fa-trash redtrash"></i>
           </a>
         </td>
       </tr>
     </tbody>
   </table>
     </transition-group>
     {{ Trashemails }}
    </div>
</template>
<script>
import { ref } from 'vue';
export default {
  props: {
    profileContactInfo: String,
    Trashemails: Array,
  },
data() {
 return {
   showTrash: true,
   mails: [
     { id: 1, sender: 'Albert.Einstein@gmail.com', subject: 'Meeting Tomorrow', content: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', priority: 'Medium', date: '02/01/2023' },
     { id: 2, sender: 'Robert.Oppenheimer@gmail.com', subject: 'Presentation Feedback', content: 'Your presentation was excellent!', date: '05/11/2023', priority: 'Medium' },
     { id: 3, sender: 'Neils.Bohr@gmail.com', subject: 'Meeting Tomorrow', content: 'Hi, let\'s discuss the project tomorrow.', date: '01/01/2023', priority: 'High' },
     { id: 4, sender: 'Lionel.Messi@gmail.com', subject: 'Presentation Feedback', content: 'Your presentation was excellent!', date: '02/01/2023', priority: 'Medium' },
   ], // Assuming mails is an array of objects
   selectedMails: []
 };
},
methods: {
 onchange() {
   // Handle checkbox change event
   // You can access selectedMails array here
   console.log(`Checkbox with id ${mailId} changed.`);
 },
 totalyRemove(mail) {
   const index = this.mails.findIndex(m => m.id === mail.id);
   if (index !== -1) {
     this.mails.splice(index, 1); // Remove the mail from the array
   }
 }
}
};
</script>

<style scoped>

.trash-container {
  max-width: 2000px;
  margin: auto;
  padding: 20px;
}
.trash-title {
  color: #187dc1;
  font-size: 28px;
  margin-bottom: 15px;
}

.prioritycol,
.contentcol,
.sendercol,
.subjectcol,
.datecol,
.selectcol,
.trashcol {
  font-weight: bold;
  color: #555;
  font-size: 20px;
  margin: 0px;
  cursor: pointer;
  position: relative;
}

.prioritycol {
  width: 100px;
}

.contentcol {
  width: 500px;
}

.sendercol,
.subjectcol {
  width: 250px;
}

.datecol {
  width: 150px;
}

.selectcol,
.trashcol {
  width: 20px;
 
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
.hoverable-row:hover {
  background-color: #f0f0f0;
}

#icon {
  font-size: 40px;
  color: #ff5555;;
  margin: 60px;
}

#icon:hover {
  color: #dd4444;
  transform: scale(1.02);
}
.trash-list {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

input {
  margin: 0px;
  padding: 0px;
  width: 20px;
  height: 20px;
  cursor: pointer;
}
</style>
