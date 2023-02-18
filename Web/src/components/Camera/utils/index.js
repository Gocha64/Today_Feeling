const drawFaceContainer = (ctx, detections) => {
    detections.forEach((detection) => {
        // do the magic
        const { topLeft, bottomRight } = detection;
        const size = [bottomRight[0] - topLeft[0], bottomRight[1] - topLeft[1]];
        // Draw a circle around the face of each person
        ctx.beginPath();
        const x = bottomRight[0] - bottomRight[0] * 0.25;
        const y = topLeft[1] + topLeft[1] * 0.3;
        const radius = size[0] * 0.6;
        ctx.lineWidth = "3";
        ctx.arc(x, y, radius, 0, Math.PI * 2);
        ctx.strokeStyle = "green";
        ctx.stroke();
    });
};
export default drawFaceContainer;
