<template>
     <h2 class="trash-title">Trash<i class="fa fa-trash redtrash" style="margin-left: 10px; font-size: 40px; color: black"></i> </h2>
        <transition-group name="fade" mode="out-in">
           <table v-if="showTrash" class="table table-dark table-bordered table-striped-columns table-hover">
        <thead>
          <tr>
            <th scope="col" class="selectcol" style="width: 50px"></th>
            <th scope="col" class="prioritycol">Priority</th>
            <th scope="col" class="sendercol">Sender</th>
            <th scope="col" class="datecol">Date</th>
            <th scope="col" class="subjectcol">Subject</th>
            <th scope="col" class="contentcol">Content</th>
            <th scope="col">Move to trash</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="mail in mails" :key="mail.id">
            <td>
              <input type="checkbox" v-model="selectedMails" :value="mail.id" @change="onchange" />
            </td>
            <td>{{ mail.priority }}</td>
            <td>{{ mail.receiver }}</td>
            <td>{{ mail.date }}</td>
            <td>{{ mail.subject }}</td>
            <td>{{ mail.content }}</td>
            <td class="align-middle">
              <a href="javascript:void(0)" @click="totalyRemove(mail)">
                <i class="fa fa-trash redtrash"></i>
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
            mails: [], // Assuming mails is an array of objects
            selectedMails: []
        };
    },
    methods: {
        onchange() {
            // Handle checkbox change event
            // You can access selectedMails array here
        },
        totalyRemove(mail) {
            // Handle removing the mail
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
  width: 80px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}
.contentcol {
  width: 300px;
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
.recievercol {
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
  width: 100px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}
.selectcol {
  width: 140px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}
.trashcol {
  width: 50px;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}

table {
  border-collapse: collapse;
  width: 100%;
  font-weight: bold;
    color: #007BFF;
    font-size: 20px;
    margin: 0px;
}
table, th, td {
  border: 1px solid;
}
.table:hover{
   transform: scale(1.02);

}
</style>