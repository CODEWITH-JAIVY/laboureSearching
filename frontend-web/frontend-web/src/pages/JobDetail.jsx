import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import {getJobById}  from "../api/job.api"

export default function JobDetail() {
  const { id } = useParams();
  const [job, setJob] = useState(null);

  useEffect(() => {
    getJobById(id).then(setJob);
  }, [id]);

  if (!job) return <p className="p-6">Loading...</p>;

  return (
    <>


      <div className="max-w-4xl mx-auto p-6">
        <div className="bg-white rounded-2xl shadow p-6">

          <h1 className="text-3xl font-bold mb-2">
            {job.role}
          </h1>

          <p className="text-gray-600">
            {job.location}
          </p>

          <p className="mt-4 text-xl font-semibold">
            ₹ {job.price} / {job.type === "DAY" ? "day" : "task"}
          </p>

          <p className="mt-4 text-gray-700">
            {job.description}
          </p>

          <div className="flex gap-2 mt-4">
            {job.skills.map((s, i) => (
              <span key={i} className="bg-blue-100 px-3 py-1 rounded-full text-sm">
                {s}
              </span>
            ))}
          </div>

          <button className="mt-6 bg-green-600 text-white px-6 py-3 rounded-xl">
            Apply Now
          </button>
        </div>
      </div>
    </>
  );
}
