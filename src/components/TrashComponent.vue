<template>
     <h2 class="trash-title">Trash<i class="fa fa-trash redtrash" style="margin-left: 10px;"></i></h2>
    
      <transition-group name="fade" mode="out-in">
        <table v-if="showTrash" class="table ">
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
          <tr v-for="mail in mails" :key="mail.id">
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
</template>

<script>
import { ref } from 'vue';


export default {
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

.trash-title {
    font-size: 30px;
    margin-bottom: 20px;
    color: #333;
  }

  .prioritycol {
  width: 100px;
  font-weight: bold;
  color: #007BFF;
  font-size: 20px;
  margin: 0px;
}
.contentcol {
  width: 500px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
    
}

.sendercol {
  width: 250px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}

.subjectcol {
  width: 250px;
  font-weight: bold;  
  color: #007BFF;
    font-size: 20px;
    margin: 0px;
}

.datecol {
  width: 180px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}
.selectcol {
  width: 20px;
  font-weight: bold;
  color: #007BFF;
  font-size: 10px;
  margin: 0px;
}

.trashcol {
  width: 20px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}

table {
  border-collapse: collapse;
  width: 100%;
  font-weight: bold;
  color: #0a0c0e;
  font-size: 20px;
  margin: 0px;
}
table, th, td {
  border: 2px solid black;
  text-align: center;
}
.table:hover{
   transform: scale(1.02);
}

#icon{
  font-size: 40px;
  color:rgb(202, 59, 59);
  margin: 60px;
}

#icon:hover{
  color: #007BFF;
  transform:scale(1.02) ;
}

input{
  margin: 5%;
}
</style>