export default function Footer() {
  return (
    <footer className="bg-gray-100 dark:bg-gray-900 border-t dark:border-gray-800 mt-10">
      <div className="max-w-7xl mx-auto px-4 py-8">

        {/* Top Section */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">

          {/* Brand */}
          <div>
            <h2 className="text-xl font-bold">
              <span className="text-blue-600">Kaam</span>
              <span className="text-orange-500">Setu</span>
            </h2>
            <p className="text-sm text-gray-600 dark:text-gray-400 mt-2">
              Local jobs & labour platform connecting workers and employers.
            </p>
          </div>

          {/* Links */}
          <div>
            <h3 className="font-semibold mb-2">Quick Links</h3>
            <ul className="space-y-2 text-sm text-gray-600 dark:text-gray-400">
              <li className="hover:text-blue-600 cursor-pointer">Home</li>
              <li className="hover:text-blue-600 cursor-pointer">Find Jobs</li>
              <li className="hover:text-blue-600 cursor-pointer">Hire Workers</li>
              <li className="hover:text-blue-600 cursor-pointer">Profile</li>
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h3 className="font-semibold mb-2">Contact</h3>
            <p className="text-sm text-gray-600 dark:text-gray-400">
              📍 India <br />
              📧 support@kaamsetu.com <br />
              📞 +91 9XXXX XXXXX
            </p>
          </div>
        </div>

        {/* Bottom */}
        <div className="border-t dark:border-gray-800 mt-8 pt-4 text-center text-sm text-gray-500">
          © {new Date().getFullYear()} KaamSetu. All rights reserved.
        </div>
      </div>
    </footer>
  );
}
