const req = require.context('./src', true, /\.svg$/)
const requireAll = requireContext => {
    console.log(requireContext)
    return requireContext.keys().map(requireContext)
}
requireAll(req)