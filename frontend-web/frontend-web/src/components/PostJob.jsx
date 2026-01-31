import { useState } from "react";
import axios from "axios";

export default function PostJob() {
  const [job, setJob] = useState({});

  const submit = async () => {
    await axios.post("http://localhost:8080/api/jobs", job, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    alert("Job Posted");
  };

  return (
    <div className="max-w-xl mx-auto px-6 py-10">
      <h2 className="text-xl font-bold mb-4">Post Job</h2>

      {["title", "city", "salary", "description"].map((f) => (
        <input
          key={f}
          placeholder={f}
          className="w-full border px-4 py-2 mb-3 rounded"
          onChange={(e) => setJob({ ...job, [f]: e.target.value })}
        />
      ))}

      <button
        onClick={submit}
        className="bg-green-600 text-white px-6 py-3 rounded"
      >
        Submit
      </button>
    </div>
  );
}
