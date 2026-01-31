import { useEffect, useState } from "react";
import JobCard from "./JobCard";
import { searchJobsService } from "../services/search.service";

export default function LatestJobs() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    searchJobsService({
      keyword: "",
      location: "",
    })
      .then(setJobs)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  return (
    <section className="max-w-7xl mx-auto px-6 py-10">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-gray-800">
          Latest Job Listings
        </h2>

        <span
          onClick={() => (window.location.href = "/jobs")}
          className="text-blue-600 cursor-pointer text-sm"
        >
          See All →
        </span>
      </div>

      {loading ? (
        <p>Loading jobs...</p>
      ) : jobs.length === 0 ? (
        <p>No jobs available</p>
      ) : (
        <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {jobs.map((job) => (
            <JobCard key={job.id} job={job} />
          ))}
        </div>
      )}
    </section>
  );
}
