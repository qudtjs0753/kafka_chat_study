import Axios from "axios";

const api = Axios.create({
  baseURL: "http://localhost:8080/kafka",
});

const chatAPI = {
  getMessages: (session) => {
    console.log("Calling get messages from API");
    return api.get(`/messages/${session}`);
  },

  sendMessage: (username, text, session) => {
    let msg = {
      author: username,
      content: text,
    };
    return api.post(`/publish`, msg, {
      headers: { "Content-Type": "application/json" },
    });
  },
};

export default chatAPI;
