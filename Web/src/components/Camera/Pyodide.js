// import { useEffect, useRef, useState } from 'react';
// import Head from 'next / head';

// export default function Pyodide({
//     pythonCode,
//     loadingMessage = 'loading…',
//     evaluatingMessage = 'evaluating…'
// }) {
//     const indexURL = 'https://cdn.jsdelivr.net/pyodide/dev/full/'
//     const pyodide = useRef(null)
//     const [isPyodideLoading, setIsPyodideLoading] = useState(true)
//     const [pyodideOutput, setPyodideOutput] =
//         useState(evaluatingMessage) // load pyodide wasm module and initialize it

//     useEffect(() => {
//         ; (async function () {
//             pyodide.current = await globalThis.loadPyodide({ indexURL })
//             setIsPyodideLoading(false)
//         })()
//     }, [pyodide]) // evaluate python code with pyodide and set output
//     useEffect(() => {
//         if (!isPyodideLoading) {
//             const evaluatePython = async (pyodide, pythonCode) => {
//                 try {
//                     return await pyodide.runPython(pythonCode)
//                 } catch (error) {
//                     console.error(error)
//                     return 'Error evaluating Python code.See console for details.'
//                 }
//             }
//                 ; (async function () {
//                     setPyodideOutput(await evaluatePython(pyodide.current,
//                         pythonCode))
//                 })()
//         }
//     }, [isPyodideLoading, pyodide, pythonCode])
//     return (
//         <>
//             <Head>
//                 <script src={`${indexURL}pyodide.js`} />
//             </Head>
//             <div>
//                 Pyodide Output: {isPyodideLoading ? loadingMessage :
//                     pyodideOutput}
//             </div>
//         </>
//     )
// }