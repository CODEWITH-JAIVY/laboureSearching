import { useNavigate } from "react-router-dom";

export default function JobCard({ job }) {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => navigate(`/jobs/${job.id}`)}
      className="bg-white rounded-xl shadow hover:shadow-lg p-5 cursor-pointer transition"
    >
      <h3 className="font-semibold text-lg">{job.title}</h3>
      <p className="text-sm text-gray-600 mt-1">{job.location}</p>

      <div className="mt-3 flex justify-between items-center">
        <span className="text-green-600 font-bold">
          ₹{job.salary}
        </span>
        <span className="text-xs bg-blue-100 text-blue-600 px-2 py-1 rounded">
          {job.type}
        </span>
      </div>
    </div>
  );
}
