export default function HireWorkers() {
  const workers = [
    { id: 1, name: "Ramesh", skill: "Electrician", city: "Indore" },
    { id: 2, name: "Suresh", skill: "Plumber", city: "Bhopal" },
  ];

  return (
    <div className="max-w-6xl mx-auto px-6 py-10">
      <h2 className="text-2xl font-bold mb-6">Hire Workers</h2>

      <div className="grid sm:grid-cols-2 md:grid-cols-3 gap-6">
        {workers.map((w) => (
          <div key={w.id} className="bg-white rounded-xl shadow p-5">
            <h3 className="font-semibold">{w.name}</h3>
            <p className="text-sm text-gray-600">
              {w.skill} • {w.city}
            </p>

            <button className="mt-4 w-full bg-blue-600 text-white py-2 rounded-lg">
              Contact
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
